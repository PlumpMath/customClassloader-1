package com.company;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;


public class Main {
private static final Logger LOG = LogManager.getLogger(Main.class);
    public static void main(String[] args) {
        boolean exit = false;
        try {
            while (!exit) {
                System.out.println("Please choose required functionality(1.jar1 2.jar2):");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                String line = bufferedReader.readLine();
                int variant = Integer.parseInt(line);
                String jarName="";
                String className="";
                String methodName="";
                boolean callMethod = false;

                if (variant == 1) {
                    LOG.debug("Number 1 chosen");
                    jarName = "c:\\Variant1.jar";
                    className = "com.module.PrinterVariant1";
                    methodName = "print";
                    callMethod = true;
                } else if (variant == 2) {
                    LOG.debug("Number 2 chosen");
                    jarName = "c:\\Variant2.jar";
                    className = "com.module.PrinterVariant2";
                    methodName = "print";
                    callMethod = true;
                } else if (variant == -1) {
                    exit = true;
                    LOG.debug("Exit utility.");
                } else {
                    LOG.warn("Incorrect variant");
                    System.out.println("Incorrect variant");
                }

                if(callMethod){
                    LOG.debug("The message is: " + methodCall(jarName, className, methodName));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return "Error";
    }
}
