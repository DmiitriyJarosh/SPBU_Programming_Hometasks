package com.company;

public class Main {

    public static void main(String[] args) {
	    HashTable<String, Integer> table = new HashTable<>(100);
	    table.AddElement("wer", 2);
	    table.AddElement("qwe", 3);
	    System.out.println(table.findByKey("qwe"));
	    table.DeleteElementByKey("qwe");
	    table.findByKey("qwe");
    }
}
