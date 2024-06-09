package org.hoey.xml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Paths;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

/**
 * @author Joy
 * @date 2020-09-06
 */
public class StaxTest {

    private static final String file = "src/main/resources/game_0.xml";
    private static final String out_file = "src/main/resources/game_1.xml";

    public static void main(String[] args) throws FileNotFoundException, XMLStreamException {
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(
            new FileInputStream(Paths.get(file).toFile()));
        String preTag = null;
        while (xmlStreamReader.hasNext()) {
            int next = xmlStreamReader.next();
            if(XMLStreamConstants.START_ELEMENT == next){
                System.out.println(xmlStreamReader.getLocalName());
                preTag = xmlStreamReader.getLocalName();
            }
            if(XMLStreamReader.CHARACTERS == next && "loc".equalsIgnoreCase(preTag)){
                System.out.println(xmlStreamReader.getText());
            }
        }

        //事件机制
        //无论标签还是文本都是元素
        //attribute是标签里的
        XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
        XMLStreamWriter writer = xmlOutputFactory.createXMLStreamWriter(new FileOutputStream(out_file));
        writer.writeStartDocument();
        writer.writeStartElement("loc");
        writer.writeCharacters("https://baidu.com");
        writer.writeEndElement();
        writer.writeEndDocument();
    }

}
