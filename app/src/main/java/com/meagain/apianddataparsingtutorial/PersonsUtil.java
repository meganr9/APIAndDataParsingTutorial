package com.meagain.apianddataparsingtutorial;

import android.util.Xml;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Megan on 2/13/2017.
 */

public class PersonsUtil {

    static public class PersonsSaxParser extends DefaultHandler {

        ArrayList<Person> personsList;
        Person person;
        StringBuilder xmlInnerText;

        public ArrayList<Person> getPersonsList() {
            return personsList;
        }

        /**
         * Receive notification of character data inside an element.
         * <p>
         * <p>By default, do nothing.  Application writers may override this
         * method to take specific actions for each chunk of character data
         * (such as adding the data to a node or buffer, or printing it to
         * a file).</p>
         *
         * @param ch     The characters.
         * @param start  The start position in the character array.
         * @param length The number of characters to use from the
         *               character array.
         * @throws SAXException Any SAX exception, possibly
         *                      wrapping another exception.
         * @see ContentHandler#characters
         */
        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            super.characters(ch, start, length);
            xmlInnerText.append(ch,start, length);
        }

        /**
         * Receive notification of the start of an element.
         * <p>
         * <p>By default, do nothing.  Application writers may override this
         * method in a subclass to take specific actions at the start of
         * each element (such as allocating a new tree node or writing
         * output to a file).</p>
         *
         * @param uri        The Namespace URI, or the empty string if the
         *                   element has no Namespace URI or if Namespace
         *                   processing is not being performed.
         * @param localName  The local name (without prefix), or the
         *                   empty string if Namespace processing is not being
         *                   performed.
         * @param qName      The qualified name (with prefix), or the
         *                   empty string if qualified names are not available.
         * @param attributes The attributes attached to the element.  If
         *                   there are no attributes, it shall be an empty
         *                   Attributes object.
         * @throws SAXException Any SAX exception, possibly
         *                      wrapping another exception.
         * @see ContentHandler#startElement
         */
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            super.startElement(uri, localName, qName, attributes);
            if (localName.equals("person")) {
                person = new Person();
                person = new Person();
                person.setId(attributes.getValue("id"));
            }

        }

        /**
         * Receive notification of the end of an element.
         * <p>
         * <p>By default, do nothing.  Application writers may override this
         * method in a subclass to take specific actions at the end of
         * each element (such as finalising a tree node or writing
         * output to a file).</p>
         *
         * @param uri       The Namespace URI, or the empty string if the
         *                  element has no Namespace URI or if Namespace
         *                  processing is not being performed.
         * @param localName The local name (without prefix), or the
         *                  empty string if Namespace processing is not being
         *                  performed.
         * @param qName     The qualified name (with prefix), or the
         *                  empty string if qualified names are not available.
         * @throws SAXException Any SAX exception, possibly
         *                      wrapping another exception.
         * @see ContentHandler#endElement
         */
        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            super.endElement(uri, localName, qName);
            if (localName.equals("person")) {
                personsList.add(person);
                person = null;
            } else if (localName.equals("name")) {
                person.setName(xmlInnerText.toString());
            } else if (localName.equals("age".trim())) {
                person.setAge(Integer.parseInt(xmlInnerText.toString().trim()));
            } else if (localName.equals("department")) {
                person.setDepartment(xmlInnerText.toString().trim());
            }
            xmlInnerText.setLength(0);
        }

        /**
         * Receive notification of the beginning of the document.
         * <p>
         * <p>By default, do nothing.  Application writers may override this
         * method in a subclass to take specific actions at the beginning
         * of a document (such as allocating the root node of a tree or
         * creating an output file).</p>
         *
         * @throws SAXException Any SAX exception, possibly
         *                      wrapping another exception.
         * @see ContentHandler#startDocument
         */
        @Override
        public void startDocument() throws SAXException {
            super.startDocument();
            personsList = new ArrayList<Person>();
            xmlInnerText = new StringBuilder();
        }


        static public ArrayList<Person> parsePerson(InputStream in) throws IOException, SAXException {
            PersonsSaxParser parser = new PersonsSaxParser();
            Xml.parse(in, Xml.Encoding.UTF_8, parser);

            return parser.getPersonsList();

        }
    }

    static public class PersonsPullParser {
        static ArrayList<Person> parsePersons(InputStream in) throws XmlPullParserException, IOException {
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(in, "UTF-8");
            Person person = null;
            ArrayList<Person> personsList = new ArrayList<Person>();
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {

                switch (event) {
                    case XmlPullParser.START_TAG:
                        if (parser.getName().equals("person")) {
                            person = new Person();
                            person.setId(parser.getAttributeValue(null, "id"));

                        } else if (parser.getName().equals("name")) {
                            person.setName(parser.nextText().trim());
                        } else if (parser.getName().equals("age")) {
                            person.setAge(Integer.parseInt(parser.nextText().trim()));
                        } else if (parser.getName().equals("department")) {
                            person.setDepartment(parser.nextText().trim());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("person")) {
                            personsList.add(person);
                            person = null;
                        }
                        break;
                    default:
                        break;
                }

                event = parser.next();
            }
            return personsList;
        }

    }
}
