#!/usr/bin/perl
#composite -gravity center smile.gif rose: rose-over.png
$file="$ARGV[0]";
$file1="$ARGV[1]";
$file2="$ARGV[2]";

`convert $file -filter point -resize 500x409 $file`;
`convert $file -resize 90x68 $file1`;
`convert $file -resize 200x150 $file2`;
