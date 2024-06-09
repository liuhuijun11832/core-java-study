package org.hoey.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * @author Joy
 * @date 2020-09-06
 */
public class DocumentBuilderTest {

    private static final String file = "src/main/resources/game_0.xml";

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();
        Document parse = documentBuilder.parse(new File(file));
        Element root = parse.getDocumentElement();
        NodeList childNodes = root.getChildNodes();
        parseChild(childNodes);
    }

    private static void parseChild(NodeList nodeList){
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node item = nodeList.item(i);
            if (item instanceof Element) {
                Element element = (Element)item;
                String tagName = element.getTagName();
                System.out.println(tagName);
                if ("loc".equals(tagName) || "lastmod".equals(tagName)) {
                    Text firstChild = (Text)element.getFirstChild();
                    String trim = firstChild.getData().trim();
                    System.out.println(trim);
                }
                NodeList subChildNode = element.getChildNodes();
                if(subChildNode != null && subChildNode.getLength() > 0){
                    parseChild(subChildNode);
                }
            }
        }
    }



}
