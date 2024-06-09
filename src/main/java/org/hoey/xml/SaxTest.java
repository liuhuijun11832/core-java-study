package org.hoey.xml;

import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * @author Joy
 * @date 2020-09-06
 */
public class SaxTest {

    private static final String file = "src/main/resources/game_0.xml";

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxParserFactory.newSAXParser();
        DefaultHandler defaultHandler = new DefaultHandler() {

            private String tag;

            private List<String> urls = new ArrayList<>();

            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes)
                throws SAXException {
                //System.out.println(uri);
                //System.out.println(localName);
                //System.out.println(qName);
                tag = qName;
            }

            @Override
            public void characters(char[] ch, int start, int length) throws SAXException {
                super.characters(ch, start, length);
                if ("loc".equals(tag)) {
                    String s = new String(ch, start, length);
                    this.urls.add(s);
                }
            }

            public String getTag() {
                return tag;
            }

            public List<String> getUrls() {
                return urls;
            }

            @Override
            public void endDocument() throws SAXException {
                super.endDocument();
                this.urls.forEach(System.out::println);
            }
        };
        saxParser.parse(new File(file), defaultHandler);
    }

    private static void parseChild(NodeList nodeList) {
    }

}
