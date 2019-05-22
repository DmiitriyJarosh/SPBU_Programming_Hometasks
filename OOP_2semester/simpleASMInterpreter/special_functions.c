#pragma once
#include "asm.h"

int checkZero()
{
    if (stack.memory[stack.esp + 1] != 0)
    {
        return 1;
    }
    else
    {
        return 0;
    }
}

void getCode()
{
    char* buf;
    buf = calloc(sizeof(char), LENGTH_OF_STRING);
    if (buf == NULL)
    {
        printf("Not enough memory for buffer");
        exit(0);
    }
    if (fgets(buf, LENGTH_OF_STRING, input) == NULL)
    {
        printf("Unexpected end of the program!!!");
        exit(0);
    }
    //gets(buf);
    code.memory[code.point] = buf;
    code.point++;
}

int charToInt(char* string, int pos)
{
    int number = 0;
    int flag = 0;
    int positive = 1;
    if (string[pos] == '-')
    {
        positive = 0;
        pos++;
    }
    while (string[pos] >= '0' && string[pos] <= '9')
    {
        number = number * 10 + string[pos] - '0';
        pos++;
        flag = 1;
    }
    if (flag == 0)
    {
        printf("Not enough arguments");
        exit(0);
    }
    while ((string[pos] != '\0') && (string[pos] != '\n'))
    {
        if ((string[pos] != ' ') && (string[pos] != '\t') && (string[pos] != '\n'))
        {
            printf("Too much arguments");
            exit(0);
        }
        pos++;
    }
    if (positive)
    {
        return number;
    }
    else
    {
        return -1 * number;
    }
}

int checkPoint(char* string)
{
    int i = 1;
    if (((string[0] >= 'a') && (string[0] <= 'z')) || ((string[0] >= 'A') && (string[0] <= 'Z')))
    {
        while((string[i] != '\0') && (string[i] != ':'))
        {
            i++;
        }
        if (string[i] == ':')
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }
    else
    {
        if (string[0] != ';')
        {
            printf("Wrong mark!");
            exit(0);
        }
        else
        {
            return 0;
        }
    }
}

int takeTag(char* string)
{
    int j, i = 0;
    char* buf = calloc(sizeof(char), LENGTH_OF_STRING);
    if (buf == NULL)
    {
        printf("Not enough memory for buffer");
        exit(0);
    }
    while (string[i] != ':' && string[i] != ' ')
    {
        buf[i] = string[i];
        i++;
    }
    if (string[i] == ' ')
    {
        printf("No spaces in tags");
        exit(0);
    }
    buf[i] = '\0';
    for (j = 0; j < code.tagsPointer; j++)
        {
            if (strcmp(buf, code.tags[j]) == 0)
            {
                return i + 2;
            }
        }
    code.tags[code.tagsPointer] = buf;
    code.tagsToLines[code.tagsPointer] = code.point - 1;
    code.tagsPointer++;
    return i + 2;
}

char* readTag(char* string, int pos)
{
    int i = 0;
    char* buf = calloc(sizeof(char), LENGTH_OF_STRING);
    if (buf == NULL)
    {
        printf("Not enough memory for buffer");
        exit(0);
    }
    while((string[pos] != '\0') && (string[pos] != ' ') && (string[pos] != '\n'))
    {
        buf[i] = string[pos];
        i++;
        pos++;
    }
    buf[i] = '\0';
    if (buf[0] == '\0')
    {
        printf("Not enough arguments");
        exit(0);
    }
    return buf;
}

