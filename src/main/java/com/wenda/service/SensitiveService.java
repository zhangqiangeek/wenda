package com.wenda.service;

import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by evilhex on 2017/12/16.
 */
@Service
public class SensitiveService implements InitializingBean{

    private static final Logger logger = LoggerFactory.getLogger(SensitiveService.class);

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            InputStream inputStream=Thread.currentThread().getContextClassLoader().getResourceAsStream("SensitiveWords.txt");
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
            String lineText;
            while ((lineText=bufferedReader.readLine())!=null){
                addWord(lineText.trim());
            }
            bufferedReader.close();
        } catch (Exception e) {
            logger.info("初始化敏感词字典树失败");
        }
    }

    /**
     * 构造树
     * @param lineText
     */
    private void addWord(String lineText){
        TrieNode tempNode=rootNode;
        for (int i=0;i<lineText.length();i++){
            Character c=lineText.charAt(i);
            if (isSymbol(c)){
                continue;
            }

            TrieNode node=tempNode.getSubNode(c);
            //判断当前节点是否已经有了子节点
            if (node==null){
                node=new TrieNode();
                tempNode.addSubNode(c,node);
            }

            tempNode=node;
            //判断是否是关键字的结尾
            if (i==(lineText.length()-1)){
                tempNode.setEnd(true);
            }
        }
    }


    private class TrieNode{
        /**
         * 关键词结尾标识
         */
        private boolean end=false;

        /**
         * 当前节点下的子节点
         */
        private Map<Character,TrieNode> subNodes=new HashMap<Character,TrieNode>();

        public void addSubNode(Character key,TrieNode node){
            subNodes.put(key,node);
        }

        public TrieNode getSubNode(Character key){
            return subNodes.get(key);
        }

        boolean isEnd(){
            return end;
        }

        void setEnd(boolean end){
            this.end=end;
        }
    }

    private TrieNode rootNode=new TrieNode();

    /**
     * 判断非正常字符
     * @param c
     * @return
     */
    private boolean isSymbol(char c){
        int ic=(int)c;
        //东亚文字 0X2E80 0x9FFF
        return !CharUtils.isAsciiAlphanumeric(c)&&(ic<0X2E80||ic>0x9FFF);
    }

    /**
     * 敏感词过滤
     * @param text
     * @return
     */
    public String filter(String text){
        StringBuilder result=new StringBuilder();
        if(StringUtils.isBlank(text)){
            return text;
        }
        String replacement="***";
        TrieNode tempNode=rootNode;
        int begin=0;
        int position=0;
        while (position<text.length()){
            char c=text.charAt(position);
            if(isSymbol(c))
            {
                if(tempNode==rootNode){
                    result.append(c);
                    ++begin;
                }
                ++position;
                continue;
            }
            tempNode=tempNode.getSubNode(c);

            if (tempNode==null){
                result.append(text.charAt(begin));
                position=begin+1;
                begin=position;
                tempNode=rootNode;
            }else if (tempNode.isEnd()){
                //敏感词的结尾
                result.append(replacement);
                position=position+1;
                begin=position;
                tempNode=rootNode;
            }else {
                ++position;
            }
        }
        return result.append(text.substring(begin)).toString();
    }
}
