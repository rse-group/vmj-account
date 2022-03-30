package vmj.object.mapper;

import java.lang.reflect.Field;
import java.util.*;

public class VMJDatabaseMapper {

    public static HashMap<String, String> convertDataType = new HashMap<String, String>();
    static {
        convertDataType.put("java.lang.String", "VARCHAR");
        convertDataType.put("int", "INTEGER");
        convertDataType.put("java.lang.Integer", "INTEGER");
        convertDataType.put("long", "BIGINT");
        convertDataType.put("boolean", "BIT");
    }

    public static void generateTable(String fullyQualifiedName, Boolean delta) {
        Class<?> implClass = createImplClass(fullyQualifiedName);
        String createTableString = generateTableQueryBaru(implClass, delta);
        System.out.println(createTableString);
        VMJDatabaseUtil vmjDBUtil = new VMJDatabaseUtil();
        vmjDBUtil.createTableFromSQL(createTableString);
    }

    public static Class<?> createImplClass(String fullyQualifiedName) {
        Class<?> implClass = null;

        try {
            implClass = Class.forName(fullyQualifiedName);
        } catch (ClassNotFoundException e) {
            // TODO: handle exception
            System.out.println("Class with FQN " + fullyQualifiedName + " not found.");
            System.exit(30);
        }

        return implClass;
    }

    public static String createFinalTableString(ArrayList<String> createTableInsideBrackets,
            ArrayList<String> foreignTableReferences, String tableName) {
        String createTableQuery = "CREATE TABLE " + tableName + " ";

        String insideBrackets = "";

        for (String string : createTableInsideBrackets) {
            // System.out.println(string);
            insideBrackets += string + ",";
        }

        if (foreignTableReferences.size() != 0) {
            for (String string : foreignTableReferences) {
                // System.out.println(string);
                insideBrackets += string + ",";
            }
        }

        String finalInsideBrackets = insideBrackets.substring(0, insideBrackets.length() - 1);
        createTableQuery += "(" + finalInsideBrackets + ");";
        return createTableQuery;
    }

    public static void getTableColumnsNamesCore(String fullyQualifiedName) {
        Class<?> implClass = createImplClass(fullyQualifiedName);
        ArrayList<String> arrayOfColumns = searchForColumns(implClass, false);
        System.out.println(fullyQualifiedName);
        for (String string : arrayOfColumns) {
            System.out.println(string);
        }
        System.out.println();
    }

    public static ArrayList<String> getTableColumnsNames(String fullyQualifiedName, Boolean methodCalledByDelta) {
        Class<?> implClass = createImplClass(fullyQualifiedName);
        ArrayList<String> arrayOfColumns = searchForColumns(implClass, methodCalledByDelta);
        return arrayOfColumns;
    }

    public static ArrayList<String> getTableColumnsNamesDelta(String fullyQualifiedName) {
        Class<?> implClass = createImplClass(fullyQualifiedName);
        ArrayList<String> arrayOfColumns = searchForColumns(implClass, true);
        return arrayOfColumns;
    }

    public static ArrayList<String> searchForColumns(Class<?> implClass, Boolean methodCalledByDelta) {
        Boolean tableAnnotationPresent = implClass.isAnnotationPresent(VMJDatabaseTable.class);
        ArrayList<String> arrayOfColumns = new ArrayList<>();

        if (!tableAnnotationPresent) {
            /**
             * no table
             */
        } else {
            VMJDatabaseTable tableAnnotation = implClass.getAnnotation(VMJDatabaseTable.class);
            Field[] classFields = implClass.getFields();

            /**
             * refactor
             */
            arrayOfColumns = findColumnsInAClass(classFields, methodCalledByDelta);
        }

        return arrayOfColumns;
    }

    public static ArrayList<String> findColumnsInAClass(Field[] classFields, Boolean methodCalledByDelta) {
        ArrayList<String> arrayOfColumns = new ArrayList<>();

        for (Field field : classFields) {

            Boolean fieldAnnotationPresent = field.isAnnotationPresent(VMJDatabaseField.class);
            VMJDatabaseField fieldAnnotation = field.getAnnotation(VMJDatabaseField.class);

            if (methodCalledByDelta) {
                if (!fieldAnnotationPresent) {

                } else {
                    if (fieldAnnotation.isDelta()) {
                        /**
                         * jalan
                         */
                        if (fieldAnnotation.primaryKey()) {
                            arrayOfColumns.add("id");
                        } else {
                            arrayOfColumns.add(field.getName());
                        }
                    } else {
                        /**
                         * gak jalan
                         */
                    }
                }
            } else {
                if (fieldAnnotationPresent) {
                    if (fieldAnnotation.primaryKey()) {
                        arrayOfColumns.add("id");
                    } else {
                        arrayOfColumns.add(field.getName());
                    }
                } else {
                    arrayOfColumns.add(field.getName());
                }
            }

        }

        return arrayOfColumns;
    }

    public static void controllerBaru(String fullyQualifiedName, Boolean delta) {
        Class<?> implClass = createImplClass(fullyQualifiedName);
        String createTableString = generateTableQueryBaru(implClass, delta);
        System.out.println(createTableString);
        VMJDatabaseUtil vmjDBUtil = new VMJDatabaseUtil();
        // vmjDBUtil.createTableFromSQL(createTableString);
    }

    public static String generateTableQueryBaru(Class<?> implClass, Boolean methodCalledByDelta) {
        Boolean tableAnnotationPresent = implClass.isAnnotationPresent(VMJDatabaseTable.class);
        String returnString = "";

        if (!tableAnnotationPresent) {
            returnString = "no table to be created";
        } else {
            VMJDatabaseTable tableAnnotation = implClass.getAnnotation(VMJDatabaseTable.class);
            Field[] classFields = implClass.getFields();

            /**
             * refactor
             */

            ArrayList<ArrayList<String>> arrayInsideBracketsAndForeignTable = createArrayInsideBracketsAndForeignTableBaru(
                    classFields, methodCalledByDelta);
            ArrayList<String> createTableInsideBrackets = arrayInsideBracketsAndForeignTable.get(0);
            ArrayList<String> foreignTableReferences = arrayInsideBracketsAndForeignTable.get(1);

            // /**
            // * refactor
            // */
            if (createTableInsideBrackets.size() != 0) {
                returnString = createFinalTableString(createTableInsideBrackets, foreignTableReferences,
                        tableAnnotation.tableName());
            } else {
                returnString = "the table has no field";
            }
        }

        return returnString;
    }

    public static ArrayList<ArrayList<String>> createArrayInsideBracketsAndForeignTableBaru(Field[] classFields,
            Boolean methodCalledByDelta) {
        ArrayList<String> createTableInsideBrackets = new ArrayList<>();
        ArrayList<String> foreignTableReferences = new ArrayList<>();
        ArrayList<ArrayList<String>> returnArrayList = new ArrayList<>();

        for (Field field : classFields) {
            Boolean fieldAnnotationPresent = field.isAnnotationPresent(VMJDatabaseField.class);

            VMJDatabaseField fieldAnnotation = field.getAnnotation(VMJDatabaseField.class);
            
            if (methodCalledByDelta) {
                if (!fieldAnnotationPresent) {

                } else {
                    if (fieldAnnotation.isDelta()) {
                        /**
                         * jalan
                         */
                        String dataType = "INTEGER";
                        if (convertDataType.containsKey(field.getType().getName())) {
                            dataType = convertDataType.get(field.getType().getName());
                        }

                        String oneInsideBracketString = field.getName() + " " + dataType;

                        if (fieldAnnotation.primaryKey()) {
                            oneInsideBracketString = "id " + dataType;
                        }

                        if (fieldAnnotation.primaryKey()) {
                            oneInsideBracketString = oneInsideBracketString + " PRIMARY KEY";
                        } else if (!fieldAnnotation.foreignTableName().equals("null")
                                & !fieldAnnotation.foreignColumnName().equals("null")) {
                            String foreignString = "FOREIGN KEY (" + field.getName() + ") REFERENCES "
                                    + fieldAnnotation.foreignTableName() + "(" + fieldAnnotation.foreignColumnName()
                                    + ")";
                            foreignTableReferences.add(foreignString);
                        }

                        createTableInsideBrackets.add(oneInsideBracketString);
                    } else {
                        /**
                         * gak jalan
                         */
                    }
                }
            } else {
                String dataType = "INTEGER";
                if (convertDataType.containsKey(field.getType().getName())) {
                    dataType = convertDataType.get(field.getType().getName());
                }

                String oneInsideBracketString = field.getName() + " " + dataType;

                if (!fieldAnnotationPresent) {

                } else {
                    if (fieldAnnotation.primaryKey()) {
                        oneInsideBracketString = "id " + dataType;
                    }

                    if (fieldAnnotation.primaryKey()) {
                        oneInsideBracketString = oneInsideBracketString + " PRIMARY KEY";
                    } else if (!fieldAnnotation.foreignTableName().equals("null")
                            & !fieldAnnotation.foreignColumnName().equals("null")) {
                        String foreignString = "FOREIGN KEY (" + field.getName() + ") REFERENCES "
                                + fieldAnnotation.foreignTableName() + "(" + fieldAnnotation.foreignColumnName() + ")";
                        foreignTableReferences.add(foreignString);
                    }
                }
                createTableInsideBrackets.add(oneInsideBracketString);
            }

        }
        returnArrayList.add(createTableInsideBrackets);
        returnArrayList.add(foreignTableReferences);
        return returnArrayList;
    }

}