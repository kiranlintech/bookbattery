#!/usr/bin/perl
#composite -gravity center smile.gif rose: rose-over.png
$file="$ARGV[0]";
$file1="$ARGV[1]";
$file2="$ARGV[2]";

`convert $file -filter point -resize 700x600 $file`;
`convert $file -resize 200x150 $file1`;
`convert $file -filter point -resize 32x32 $file2`;