#pragma once
#ifndef ASM_H_INCLUDED
#define ASM_H_INCLUDED


#define SIZE_OF_HEAP 0x80000
#define SIZE_OF_STACK 1000
#define SIZE_OF_CODE 1000
#define LENGTH_OF_STRING 256


struct stack
{
    int* memory;
    int esp;
}stack;

struct heap
{
    int* memory;
}heap;

struct code
{
    char** memory;
    int point;
    int IP;
    int* tagsToLines;
    char** tags;
    int tagsPointer;
}code;



#include <stdio.h>
FILE* input;


void init();
void ld(int adr);
void st(int adr);
void ldc(int num);
void add();
void sub();
void cmp();
void br(char* tag);
void jmp(char* tag);
int checkZero();
void getCode();
int charToInt(char* string, int pos);
int checkPoint(char* string);
char* readTag(char* string, int pos);
int takeTag(char* string);
int analyzeAndDoCode(int IP);
void print();


#endif // ASM_H_INCLUDED
