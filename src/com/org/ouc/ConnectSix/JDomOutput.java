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
        //创建文档
        Document document = new Document();
        //创建根元素
        Element head = new Element("head");
        //把根元素加入到document中
        document.addContent(head);

        //创建注释
        //Comment rootComment = new Comment("将数据从程序输出到XML中！");
        //head.addContent(rootComment);

        //创建父元素
        Element phaselist = new Element("phaselist");
        Element phase = new Element("phase");
        Element content = new Element("content");
        List<Phase> ps = new ArrayList<Phase>();

        //把元素加入到根元素中
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





        //设置xml输出格式
        Format format = Format.getPrettyFormat();
        format.setEncoding("utf-8");//设置编码
        format.setIndent("    ");//设置缩进


        //得到xml输出流
        XMLOutputter out = new XMLOutputter(format);
        //把数据输出到xml中
        out.output(document, new FileOutputStream("src\\com\\org\\ouc\\ConnectSix\\test.xml"));//或者FileWriter

    }

} 