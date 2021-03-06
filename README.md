# Attempt to fix grammar for a StackOverflow question

## Overview

[Here](http://www.antlr3.org/grammar/list.html) you can find ISO SQL 2003 grammar created by Douglas Godfrey. If you try to compile java code generated by ANTLRv3.3 on the basis of this grammar, you get a lot of errors described in [this stackoverflow question](http://stackoverflow.com/q/34202657).

In this repository you can find fixed grammars (to the extent described below) and manually modified java code of the parser. The former allows to generate syntactically correct Java code from the grammar. The latter allows to compile it taking into account Java bytecode’s restrictions ([code size](http://stackoverflow.com/a/2408005)). It also features demo console app, which prints a parse tree for given SQL file.

## What is fixed and what is not

First, I started to fix obvious errors of the grammar, which led to the compilation errors in generated java code. These were the same error that the author of the question has faced. Eventually I have fixed all Java syntax errors but faced another one which it impossible to fix directly because it originates from limitation of JVM, the`compilation error: code too large`. Reading ANTLR mailing list there was a hint to extract some static members of the huge classes into separate interfaces and “implement” them to have a sort of multiple inheritance. With trial and error I ended up with 6 interfaces ”imlemented” by parser in `sql2003Parser.java`.
But still there are 2 problems:

 * **Wrong start rule.** Douglas Godfrey wrote grammar that starts with "sql2003Parser" rule. Unfortunately if you call parser by this start rule, it won’t parse correctly even simplest `select a from b`. So I call parser by `query_specification` rule to parse `SELECT` clause only.
 * **Some other errors in grammar.** I didn’t dig too deep in the grammar but `query_specification` fails to parse some random complex SQLs.

## Repository overview

 * `sql2003Lexer.g` and `sql2003Parser.g`: fixed grammars
 * `java/sql2003Lexer.java`: lexer Java code generated by antlr-3.3
 * `java/sql2003Lexer.tokens` and `sql2003Parser.tokens`: files generated by antlr-3.3
 * `java/ANTLRDemo.java`: demo console app, which prints a parse tree for given SQL file
 * `java/Part1.java` ... `java/Part6.java`: 6 interfaces with extracted static members
 * `java/Test.sql`:  sample SQL
 * `java/sql2003Parser.java`: modified Java code of parser. 

## How to run it

```shell
$ javac sql2003Lexer.java Part1.java Part2.java Part3.java Part4.java Part5.java Part6.java sql2003Parser.java  ANTLRDemo.java
$ java ANTLRDemo Test.sql
```
