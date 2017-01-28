package net.vjdv.xmiviewer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import net.vjdv.xmiviewer.modelos.XMI;

/**
 *
 * @author Jassiel
 */
public class TraductorXMI {
  public static XMI file2modelo(File file){
    try {
      JAXBContext context = JAXBContext.newInstance(XMI.class);
      Unmarshaller u = context.createUnmarshaller();
      return (XMI) u.unmarshal(new FileInputStream(file));
    }catch(JAXBException | FileNotFoundException ex){
      Logger.getLogger("XMI").log(Level.WARNING, null, ex);
      return null;
    }
  }
  public static boolean modelo2file(XMI xmi, File file){
    try {
      JAXBContext jaxbContext = JAXBContext.newInstance(XMI.class);
      Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
      jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//      jaxbMarshaller.marshal(xmi,System.out);
      jaxbMarshaller.marshal(xmi,file);
      return true;
    }catch(Exception ex){
      Logger.getLogger("XMI").log(Level.WARNING, null, ex);
      return false;
    }
  }
}