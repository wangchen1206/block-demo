package com.cc.block.demo.entity;

import com.cc.block.demo.util.StringUtil;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @Author wangchen95
 * @Description 区块
 * @Date 2021/9/26
 **/
public class Block {
    public String hash;
    public String previousHash;
    private String data; //our data will be a simple message.
    private long timeStamp;
    private int nonce;

    public Block(String data, String previousHash) {
        this.previousHash = previousHash;
        this.data = data;
        this.timeStamp = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        this.hash = calculateHash();
    }

    public String calculateHash() {
        String calculatedhash = StringUtil.applySha256(
                previousHash +
                        Long.toString(timeStamp) +
                        Integer.toString(nonce) +
                        data
        );
        return calculatedhash;
    }

    //工作量证明  挖矿
    public void mineBlock(int difficulty) {
        char[] chars = new char[difficulty];
        String s = new String(chars);
        String target = s.replace('\0', '0'); //Create a string with difficulty * "0"
        while(!hash.substring( 0, difficulty).equals(target)) {
            nonce ++;
            hash = calculateHash();
        }
        System.out.println("Block Mined!!! : " + hash);
    }

}
