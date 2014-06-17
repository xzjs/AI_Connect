package com.org.ouc.ConnectSix;

import java.io.FileInputStream;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;




public class SaxParseService extends DefaultHandler{
    private static List<PhaseList> phaseLists = null;
    private static PhaseList phaseList = null;
    private static Phase phase = null;
    private static String preTag = null;//作用是记录解析时的上一个节点名称

    public List<PhaseList> getPhaseLists(InputStream xmlStream) throws Exception{
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser;

        parser = factory.newSAXParser();
        SaxParseService handler = new SaxParseService();
        parser.parse(new FileInputStream("src\\com\\org\\ouc\\ConnectSix\\test.xml"), handler);


        return phaseLists;

    }


    @Override
    public void startDocument() throws SAXException {
        phaseLists = new ArrayList<PhaseList>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {


        if("phaselist".equals(qName)){
            phaseList = new PhaseList();
            phaseList.setIsWin(Integer.parseInt(attributes.getValue(0)));
        }
        if("phase".equals(qName)){
            phase = new Phase();
        }
        preTag = qName;//将正在解析的节点名称赋给preTag 
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {



        if("phase".equals(qName)){
            phaseList.addPhase(phase);
            phase = null;
        }
        if("phaselist".equals(qName)){
            phaseLists.add(phaseList);
            phaseList = null;
        }
        preTag = null;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if(preTag!=null){
            String content = new String(ch,start,length);
            if("content".equals(preTag)){
                phase.setPhaseString(content);
            }
        }
    }

}