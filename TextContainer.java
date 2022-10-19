import java.io.*;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@SaveToAnnotation
public class TextContainer implements Serializable{
   private static String text = "smthng";
   private static String path = "C:\\smthng.txt";

   public static String getFieldOne() {
      return fieldOne;
   }

   @Save
   private static String fieldOne = "fieldOne";
   @Save
   private int fieldTwo = 2;

   private boolean fieldThree = true;
   @Save
   private char fieldFour = 'd';

   @SaverAnnotation
   public static void save (String path) throws IOException {
      try (FileWriter writer = new FileWriter(path)){
         writer.write(text);
      }
   }

   public static String getPath() {
      return path;
   }

   public static void setFieldOne(String fieldOne) {
      TextContainer.fieldOne = fieldOne;
   }

   public void setFieldTwo(int fieldTwo) {
      this.fieldTwo = fieldTwo;
   }

   public void setFieldThree(boolean fieldThree) {
      this.fieldThree = fieldThree;
   }

   public void setFieldFour(char fieldFour) {
      this.fieldFour = fieldFour;
   }

   @Override
   public String toString() {
      return "TextContainer{" +
              "fieldTwo=" + fieldTwo +
              ", fieldThree=" + fieldThree +
              ", fieldFour=" + fieldFour +
              '}';
   }

   @Target(value = ElementType.FIELD)
   @Retention(value = RetentionPolicy.RUNTIME)
   public @interface Save {
   }
}
