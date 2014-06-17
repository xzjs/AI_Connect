package com.org.ouc.ConnectSix;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Attribute;
import org.jdom.Comment;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class JDomOutput
{
    public void add(List<PhaseList> phaseLists) throws IOException
    {
        //�����ĵ�
        Document document = new Document();
        //������Ԫ��
        Element head = new Element("head");
        //�Ѹ�Ԫ�ؼ��뵽document��
        document.addContent(head);

        //����ע��
        //Comment rootComment = new Comment("�����ݴӳ��������XML�У�");
        //head.addContent(rootComment);

        //������Ԫ��
        Element phaselist = new Element("phaselist");
        Element phase = new Element("phase");
        Element content = new Element("content");
        List<Phase> ps = new ArrayList<Phase>();

        //��Ԫ�ؼ��뵽��Ԫ����
        for(PhaseList pList : phaseLists){
            phaselist = new Element("phaselist");
            phaselist.setAttribute("win", Integer.toString(pList.getIsWin()));
            ps = pList.getPhases();
            for(Phase p : ps){
                phase = new Element("phase");
                content = new Element("content");
                content.setText(p.getPhaseString());
                phase.addContent(content);
                phaselist.addContent(phase);
                phase = null;
                content = null;
            }
            head.addContent(phaselist);
            phaselist = null;
        }





        //����xml�����ʽ
        Format format = Format.getPrettyFormat();
        format.setEncoding("utf-8");//���ñ���
        format.setIndent("    ");//��������


        //�õ�xml�����
        XMLOutputter out = new XMLOutputter(format);
        //�����������xml��
        out.output(document, new FileOutputStream("src\\com\\org\\ouc\\ConnectSix\\test.xml"));//����FileWriter

    }

} 