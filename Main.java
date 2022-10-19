import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class Main {
    @Target(value = ElementType.METHOD)
    @Retention(value = RetentionPolicy.RUNTIME)
    public @interface Test {
        int a() default 2;
        int b() default 33;
    }
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        Class<?> cl = Main.class;

        Method[] methods = cl.getDeclaredMethods();
        for (Method m : methods) {
            if (m.isAnnotationPresent(Test.class)) {
                Test geronimo = m.getAnnotation(Test.class);
                System.out.println(m.invoke(null, geronimo.a(), geronimo.b()));
            }
        }

        //
        //SCND task
        //

        Class <?> clss = TextContainer.class;

        Method[] textContainerMethods = clss.getDeclaredMethods();
        for (Method m : textContainerMethods) {
            if (m.isAnnotationPresent(SaverAnnotation.class)){
                System.out.println(m.getName());
                m.invoke(null, SaveToAnnotation.path);
            }
        }
        try {
            TextContainer.save(TextContainer.getPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //
        //        THRD TASK
        //

        TextContainer objForSerialization = new TextContainer();
        Map <String, String> annotaitedFields = new HashMap<>();
        Field[] txtContFields = objForSerialization.getClass().getDeclaredFields();
        for (Field f : txtContFields){
            if (f.isAnnotationPresent(TextContainer.Save.class)){
                if(Modifier.isPrivate(f.getModifiers())){
                    f.setAccessible(true);
                }
                annotaitedFields.put(f.getName(), String.valueOf(f.get(objForSerialization)));
                }
            }
        System.out.println(annotaitedFields);
        TextContainer objForDeSerialization = new TextContainer();
        objForDeSerialization.setFieldFour('a');
        objForDeSerialization.setFieldTwo(33);
        try {
            Field fieldOne = clss.getDeclaredField("fieldOne");
            fieldOne.setAccessible(true);
            fieldOne.set(null, "fieldTwenty");
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        System.out.println("before deserialization: " + objForDeSerialization + " " + TextContainer.getFieldOne());
        for(Field f : txtContFields){
        if(annotaitedFields.containsKey(f.getName())){
            try {
                Field temporaryField = clss.getDeclaredField(f.getName());
                if(Modifier.isPrivate(temporaryField.getModifiers())){
                    temporaryField.setAccessible(true);
                }
                if(temporaryField.getType().toString().equals("int")){
                    int tempInt = Integer.parseInt(annotaitedFields.get(f.getName()));
                    if(!Modifier.isStatic(temporaryField.getModifiers())){
                        temporaryField.set(objForDeSerialization, tempInt);
                    } else temporaryField.set(null, tempInt);
                }
                if(temporaryField.getType().toString().equals("char")){
                    char tempChar = annotaitedFields.get(f.getName()).charAt(0);
                    if(!Modifier.isStatic(temporaryField.getModifiers())){
                        temporaryField.set(objForDeSerialization, tempChar);
                    } else temporaryField.set(null, tempChar);
                }
                if(temporaryField.getType().toString().equals("class java.lang.String")){
                    if(!Modifier.isStatic(temporaryField.getModifiers())){
                        temporaryField.set("tempString" + objForDeSerialization, annotaitedFields.get(f.getName()));
                    } else temporaryField.set(null, annotaitedFields.get(f.getName()));
                }
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
                }
            }
        }
        System.out.println("after deserialization: " + objForDeSerialization + " " + TextContainer.getFieldOne());
    }
    @Test
    public static int oneTwoOneTwo (int a, int b) {
       return a + b;
    }
}
