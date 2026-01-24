#!/usr/bin/perl
#composite -gravity center smile.gif rose: rose-over.png
$file="$ARGV[0]";
$file1="$ARGV[1]";
$file2="$ARGV[2]";

`convert $file -filter point -resize 450x450\! $file`;
`convert $file -resize 100x100\! $file1`;
`convert $file -filter point -resize 90x100\! $file2`;