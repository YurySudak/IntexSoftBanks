package sudak;

import lombok.Getter;
import lombok.SneakyThrows;
import org.h2.tools.RunScript;
import sudak.command.Command;
import sudak.repository.Repository;
import sudak.service.Service;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.*;

public class ApplicationContext {
    private static Connection connection;
    @Getter
    private CommandHandler commandHandler;
    private Map<String, Repository> repositories;
    private Map<String, Service> services;
    private Set<Command> commands;

    public static void run() {
        ApplicationContext context = new ApplicationContext();
        context.start();
        context.getCommandHandler().run();
        context.stop();
    }

    private void start() {
        createConnection("bankdb");
        initializeDatabase("init.sql");
        injectConnectionToRepositories();
        injectRepositoriesToServices();
        injectServicesToCommands();
        registerCommandsInCommandHandler();
    }

    @SneakyThrows
    private void createConnection(String dbName) {
        connection = DriverManager.getConnection("jdbc:h2:./" + dbName, "sa", "");
    }

    @SneakyThrows
    private void initializeDatabase(String fileName) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        try (Reader reader = new FileReader(file)) {
            RunScript.execute(connection, reader);
        }
    }

    @SneakyThrows
    private void injectConnectionToRepositories() {
        repositories = new HashMap<>();
        List<Class<?>> classes = findClasses("repository");
        for (Class<?> clazz : classes) {
            Constructor<?>[] constructors = clazz.getConstructors();
            for (Constructor<?> constructor : constructors) {
                if (constructor.getParameterCount() == 1) {
                    Repository repository = (Repository) constructor.newInstance(connection);
                    repositories.put(repository.getClass().getSimpleName(), repository);
                }
            }
        }
    }

    @SneakyThrows
    private void injectRepositoriesToServices() {
        services = new HashMap<>();
        List<Class<?>> classes = findClasses("service");
        for (Class<?> clazz : classes) {
            Constructor<?>[] constructors = clazz.getConstructors();
            for (Constructor<?> constructor : constructors) {
                if (constructor.getParameterCount() > 0) {
                    List<Repository> repositoriesList = new ArrayList<>();
                    for (Class<?> parameterType : constructor.getParameterTypes()) {
                        repositoriesList.add(repositories.get(parameterType.getSimpleName()));
                    }
                    Service service = (Service) constructor.newInstance(repositoriesList.toArray());
                    services.put(service.getClass().getSimpleName(), service);
                }
            }
        }
    }

    @SneakyThrows
    private void injectServicesToCommands() {
        commands = new HashSet<>();
        List<Class<?>> classes = findClasses("command");
        for (Class<?> clazz : classes) {
            Constructor<?>[] constructors = clazz.getConstructors();
            for (Constructor<?> constructor : constructors) {
                if (constructor.getParameterCount() > 0) {
                    List<Service> servicesList = new ArrayList<>();
                    for (Class<?> parameterType : constructor.getParameterTypes()) {
                        servicesList.add(services.get(parameterType.getSimpleName()));
                    }
                    Command command = (Command) constructor.newInstance(servicesList.toArray());
                    commands.add(command);
                }
            }
        }
    }

    @SneakyThrows
    private void registerCommandsInCommandHandler() {
        commandHandler = new CommandHandler();
        for (Command command : commands) {
            commandHandler.registerCommand(command);
        }
    }

    @SneakyThrows
    private List<Class<?>> findClasses(String packageName) {
        packageName = "sudak." + packageName;
        List<Class<?>> classes = new ArrayList<>();
        String path = packageName.replace(".", "/");;
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        File directory = new File(classLoader.getResource(path).getFile());

        if (directory.exists()) {
            Queue<File> directories = new LinkedList<>();
            directories.add(directory);

            while (!directories.isEmpty()) {
                File currentDirectory = directories.poll();
                File[] files = currentDirectory.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.isDirectory()) {
                            directories.add(file);
                        } else if (file.isFile() && file.getName().endsWith(".class")) {
                            String className = packageName + '.' +
                                    file.getPath().substring(directory.getPath().length() + 1, file.getPath().length() - 6)
                                            .replace(File.separatorChar, '.');
                            classes.add(Class.forName(className));
                        }
                    }
                }
            }
        }
        return classes;
    }

    @SneakyThrows
    private void stop() {
        connection.close();
        System.gc();
    }
}
