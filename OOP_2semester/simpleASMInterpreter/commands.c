#pragma once
#include "asm.h"

void init()
{
    int i;
    stack.memory = (int*)malloc(sizeof(int) * SIZE_OF_STACK);
    if (stack.memory == NULL)
    {
        printf("Not enough memory for stack");
        exit(0);
    }
    stack.esp = SIZE_OF_STACK - 1;
    heap.memory = (int*)malloc(sizeof(int) * SIZE_OF_HEAP);
    if (heap.memory == NULL)
    {
        printf("Not enough memory for heap");
        exit(0);
    }
    code.memory = (char**)calloc(sizeof(char*), SIZE_OF_CODE);
    if (code.memory == NULL)
    {
        printf("Not enough memory for code");
        exit(0);
    }
    code.point = 0;
    code.IP = 0;
    code.tagsToLines = (int*)calloc(sizeof(int), SIZE_OF_CODE);
    if (code.tagsToLines == NULL)
    {
        printf("Not enough memory for code");
        exit(0);
    }
    code.tags = (char**)calloc(sizeof(char*), SIZE_OF_CODE);
    if (code.tags == NULL)
    {
        printf("Not enough memory for code");
        exit(0);
    }
    code.tagsPointer = 0;
}

void deinit()
{
    int i;
    free(stack.memory);
    free(heap.memory);
    free(code.tagsToLines);
    for (i = 0; i < code.point; i++)
    {
        free(code.memory[i]);
    }
    free(code.memory);
    for (i = 0; i < code.tagsPointer; i++)
    {
        free(code.tags[i]);
    }
    free(code.tags);
}

void ld(int adr)
{
    if (stack.esp == -1)
    {
        printf("Stack error\n");
        exit(0);
    }
    if ((adr < 0) || (adr > SIZE_OF_HEAP - 1))
    {
        printf("Memory address error\n");
        exit(0);
    }
    stack.memory[stack.esp] = heap.memory[adr];
    stack.esp--;
}

void st(int adr)
{
    if (stack.esp == SIZE_OF_STACK - 1)
    {
        printf("Stack error\n");
        exit(0);
    }
    if ((adr < 0) || (adr > SIZE_OF_HEAP - 1))
    {
        printf("Memory address error\n");
        exit(0);
    }
    heap.memory[adr] = stack.memory[stack.esp + 1];
    stack.esp++;
}

void print()
{
    printf("%d\n", stack.memory[stack.esp + 1]);
}

void ldc(int num)
{
    if (stack.esp == -1)
    {
        printf("Stack error\n");
        exit(0);
    }
    stack.memory[stack.esp] = num;
    stack.esp--;
}

void add()
{
    if ((stack.esp >= SIZE_OF_STACK - 2) || (stack.esp == -1))
    {
        printf("Stack error\n");
        exit(0);
    }
    stack.memory[stack.esp + 2] = stack.memory[stack.esp + 1] + stack.memory[stack.esp + 2];
    stack.esp++;
}

void sub()
{
    if ((stack.esp >= SIZE_OF_STACK - 2) || (stack.esp == -1))
    {
        printf("Stack error\n");
        exit(0);
    }
    stack.memory[stack.esp + 2] = stack.memory[stack.esp + 1] - stack.memory[stack.esp + 2];
    stack.esp++;
}

void cmp()
{
    if ((stack.esp >= SIZE_OF_STACK - 2) || (stack.esp == -1))
    {
        printf("Stack error\n");
        exit(0);
    }
    if (stack.memory[stack.esp + 1] > stack.memory[stack.esp + 2])
    {
        stack.memory[stack.esp + 2] = 1;
        stack.esp++;
    }
    else
    {
        if (stack.memory[stack.esp + 1] < stack.memory[stack.esp + 2])
        {
            stack.memory[stack.esp + 2] = -1;
            stack.esp++;
        }
        else
        {
            stack.memory[stack.esp + 2] = 0;
            stack.esp++;
        }
    }
}

void br(char* tag)
{
    if (checkZero())
    {
        jmp(tag);
    }
}

void jmp(char* tag)
{
    int i;
    int flag = 0;
    for (i = 0; i < code.tagsPointer; i++)
        {
            if (strcmp(tag, code.tags[i]) == 0)
            {
                code.IP = code.tagsToLines[i];
                flag = 1;
                break;
            }
        }
    if (!flag)
    {
        while (1)
        {
            getCode();
            if (checkPoint(code.memory[code.point - 1]))
            {
                takeTag(code.memory[code.point - 1]);
                if (strcmp(tag, code.tags[code.tagsPointer - 1]) == 0)
                {
                    code.IP = code.tagsToLines[code.tagsPointer - 1];
                    break;
                }
            }
        }
    }
}
