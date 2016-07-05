[![Build Status](https://travis-ci.org/travismontoya/ldsreader.svg?branch=master)](https://travis-ci.org/travismontoya/ldsreader)
ldsreader
=========
Search the Prohphet and Apostles recent messages and download the pdf content.

### Usage

This project uses sbt to manage the project. It makes it very simple to run, compile or package it as a jar file.

##### To package as a jar file
```
ldsreader tmontoya$ sbt package
ldsreader tmontoya$ scala target/scala-2.11/ldsreader_2.11-1.0.jar
```

##### To run the program through sbt
```
ldsreader tmontoya$ sbt run
......
Search recent messages by the Prophet and Apostles
To download a talk paste the link into the search and press enter
Updating database! This can take a while...Done!

Search (Type 'exit' to quit): Heart
Sharing the Gospel Heart to Heart
https://www.lds.org/liahona/2012/09/sharing-the-gospel-heart-to-heart?lang=eng

A Heart Filled with Love
https://www.lds.org/friend/2013/02/a-heart-filled-with-love?lang=eng

“As He Thinketh in His Heart”
https://www.lds.org/prophets-and-apostles/unto-all-the-world/as-he-thinketh-in-his-heart-?lang=eng

A Grateful Heart
https://www.lds.org/friend/2013/11/a-grateful-heart?lang=eng


Search (Type 'exit' to quit): https://www.lds.org/friend/2013/11/a-grateful-heart?lang=eng
Found PDF. Downloading to 2013-11-03-a-grateful-heart-eng.pdf

Search (Type 'exit' to quit):
```
