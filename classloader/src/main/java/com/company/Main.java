package com.company;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;


public class Main {
private static final Logger LOG = LogManager.getLogger(Main.class);
    public static void main(String[] args) {
        boolean exit = false;
        Properties prop = new Properties();

        try (InputStream input = new FileInputStream("config.properties");
             BufferedReader bufferedReader =
                     new BufferedReader(new InputStreamReader(System.in))) {
            prop.load(input);
            String urlLib1 = prop.getProperty("jar1");
            String urlLib2 = prop.getProperty("jar2");

            while (!exit) {
                System.out.println("Please choose required functionality(1.jar1 2.jar2):");
                String line = bufferedReader.readLine();
                int variant = Integer.parseInt(line);
                String jarName = "";
                String className = "";
                String methodName = "";
                boolean callMethod = false;

                switch (variant) {
                    case 1:
                        LOG.debug("Number 1 chosen");
                        jarName = urlLib1;
                        className = "com.module.PrinterVariant1";
                        methodName = "print";
                        callMethod = true;
                        break;
                    case 2:
                        LOG.debug("Number 2 chosen");
                        jarName = urlLib2;
                        className = "com.module.PrinterVariant2";
                        methodName = "print";
                        callMethod = true;
                        break;
                    case -1:
                        exit = true;
                        LOG.debug("Exit utility.");
                        break;
                    default:
                        LOG.warn("Incorrect variant");
                        System.out.println("Incorrect variant");
                        break;

                }

                if (callMethod) {
                    LOG.debug("The message is: " + methodCall(jarName, className, methodName));
                }
            }
        } catch (IOException|NumberFormatException e) {
            LOG.error(e.getMessage(),e);
        }
    }

    private static String methodCall(String jarUrl, String className, String methodName){
        try{
            File file  = new File(jarUrl);

            URL url = file.toURI().toURL();
            URL[] urls = new URL[]{url};

            ClassLoader cl = new URLClassLoader(urls);
            Class cls = cl.loadClass(className);
            return cls.getMethod(methodName).invoke(cls.newInstance()).toString();
        } catch (ClassNotFoundException|NoSuchMethodException|InvocationTargetException|
                IllegalAccessException|InstantiationException|MalformedURLException e) {
            LOG.error(e.getMessage(),e);
        }
        return "Error";
    }
}
