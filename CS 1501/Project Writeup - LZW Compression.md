# Leo Zhang - LIZ142 - Assignment 3 Writeup

  In this assignment, after tabulating the results I noticed that the four variants of LZW performed quite similarly on certain files, but on other files there was a large difference in performance and compression ratios. To start, I will explain my implementation and how I got to my completed LZW. 
  
 DLBCodeBook is used for compression, and much of the work here was modifying the public add method to implement flushing logic and resizing of the codebook. Whenever we run out of codewords but we aren't at the maximum number of bits, we simply increase the width of the codeword, which effectively doubles the number of usable codewords since the number of usable codewords is 2^W. If we are given the permission to flush the codebook (resetting it), we do so when we are at the maximum number of codewords and our codeword width is already at maximum. If we are not given permission to flush, then we can no longer add codewords and we are compressing the rest of the file with the existing codewords.
 
 ArrayCodeBook is used for decompression. Similarly to DLBCodeBook, the modifications took place in the public add method, where resizing and flushing logic is just about the same. However, in ArrayCodeBook the logic for getting the codeword width is more complex because expansion is always one step behind compression. Whenever the codebook is resized, we return W+1 in order to keep the compression and expansion in sync, and we return MinW if we are allowed to flush and when all codewords are used and we are maximum codeword width.
 
 I also made changes to LZW.java, mostly to account for the variability of the codeword width. Each codebook is now initialized with a starting width of 9 and a max of 16, and I also added a command line argument to check whether flushing the codebook is allowed or not. If there is an "r" in the argument for compression, this means we allow flushing. Anything else means we are not allowing flushing. I also print an "R" if reset is allowed and "N" if compression is not allowed for the first character of the LZW compressed file to tell the decompression algorithm whether resets are allowed or not, thus keeping the compression and decompression in sync. When we start decompressing, we read the first character of the file and check whether we have "R" or "N", setting the flushIfFull boolean to true and false respectively.  
  
  
 ## Performance Discussion
  
 For our default, 12-bit version of LZW, we had the worst compression ratios overall; in all of the the tests the ratio was the lowest (and thus the worst). This makes sense because the codebook is a fixed length and runs out fairly quickly at this width, given the total number of codewords is 2^W. 
 
  The 9-to-16 LZW implementation with no reset did fairly well on most of the tests, doing far better than the 12-bit LZW in most files. This is most pronounced on tests like winnt256.bmp, where the modified LZW performed more than two times better than the 12-bit LZW. However, I noticed during testing that the 9-to-16 bit LZW didn't actually produce a working result on the large.txt test, as it ran out of codeword space and was unable to add compression codewords once the codebook filled up with the maximum number of codes at 16 bits. Thus, after running out of codewords the output is essentially gibberish text, as the rest of the file is rather different from the first half despite it all being in English.
  
  To fix the problem of running out of codewords, the 9-to-16 bit LZW with a reset allows us to flush the codebook and start generating new codewords as soon as the codebook fills up at 16 bits. This means we are able to compress files that are much larger than without a reset. This version of compress doesn't produce different results than the version without a reset for smaller files, because they are essentially the same for this matter, but on larger files we do see a slight improvement of the compression ratios (files like edit.exe and large.txt) as the reset ensures we can keep compressing and adding codewords to the codebook. 
  
  Despite the improvements, there are anomalous files such as Lego-big.gif and frosty.jpg, which actually cannot be compressed by LZW. In all our implementations the compression actually produces a ratio that is below 1, implying compression was NOT successful and in fact we actually made the file bigger than before. The reason why this happened is because the formats gif and jpg are not lossless, and they are already compressed in a lossy manner; therefore, by trying to run LZW on these files we are essentially trying to compress a compressed file and the output is made larger. 
  
  On the other hand, wacky.bmp is a file that is able to be compressed exceptionally well, with a ratio of over 200 on all implementations of LZW. I believe the reason this is possible is because of the large amount of blank space within the file; because of this, LZW is able to achieve very long codeword strings and thus is able to compress large sections of the file with ease.
  
 Unix compress seems to be able to avoid this inadvertent expansion of these already compressed files by simply doing nothing at all! This is shown where the Unix compress gives a ratio of 1.0, or the original size. In general, however, Unix compress gives a result very similar to our 9-to-16 bit LZW implementation with a reset, albeit improving slightly on certain compresses by only a few bits. For the most part, the compression ratios between Unix compress and the 9-to-16 bit LZW with reset is the same. I have a suspicion that Unix compress may just be a repackaged LZW with a reset, further modified with some minimal improvements to compression and definitely with added logic to avoid compressing a file that is already compressed.

## Compression Test Results
| File Name | 12-bit Provided LZW | 9 to 16-bit LZW w/o reset | 9 to 16-bit LZW w/ reset | Unix compress |
| -- | -- | -- | -- | -- |
| Lego-big.gif |  128,973 bytes - Ratio 0.72  | 122,493 bytes - Ratio 0.95 | 122,493 bytes - Ratio 0.95 |  93,371 bytes - Ratio 1.00  |
| all.tar | 1,846,854 bytes - Ratio 1.64 |  1,792,781 bytes - Ratio 1.69 |  1,178,221 bytes - Ratio 2.57 |  1,179,467 bytes - Ratio 2.57 |
| assig2.doc | 74,574 bytes - Ratio 1.17 | 40,040 bytes - Ratio 2.17 | 40,040 bytes - Ratio 2.17 | 40,040 bytes - Ratio 2.17 |
| bmps.tar | 925,079 bytes - Ratio 1.20 | 80,913 bytes - Ratio 13.67 | 80,913 bytes - Ratio 13.67 | 80,913 bytes - Ratio 13.67 |
| code.txt | 30,852 bytes - Ratio 2.25 | 24,291 bytes - Ratio 2.86 | 24,291 bytes - Ratio 2.86 | 24,291 bytes - Ratio 2.86  |
| code2.txt | 23,775 bytes - Ratio 2.32 | 20,318 bytes - Ratio 2.72  | 20,318 bytes - Ratio 2.72 | 20,319 bytes - Ratio 2.72 |
| edit.exe | 250,742 bytes - Ratio 0.94 | 156,409 bytes - Ratio 1.51 | 152,231 bytes - Ratio 1.55 | 151,111 bytes - Ratio 1.56  |
| frosty.jpg | 177,453 bytes - Ratio 0.71 | 163,789 bytes - Ratio 0.77 | 171,170 bytes - Ratio 0.74 | 126,748 bytes - Ratio 1.00 |
| gone_fishing.bmp | 9,278 bytes - Ratio 1.86| 8,963 bytes - Ratio 1.93| 8,963 bytes - Ratio 1.93 | 8,964 bytes - Ratio 1.93 |
| large.txt | 599,406 bytes - Ratio 2.01 | 497,971 bytes - Ratio 2.42 | 523,715 bytes - Ratio 2.30 | 519,465 byte - Ratio 2.32 |
| medium.txt | 13,076 bytes - Ratio 1.88 | 12,440 bytes - Ratio 1.98 | 12,440 bytes - Ratio 1.98 | 12,440 bytes - Ratio 1.98 |
| texts.tar | 1,012,179 bytes - Ratio 1.37| 597,847 bytes - Ratio 2.31 | 590,559 bytes - Ratio 2.34 | 589,697 bytes - Ratio 2.34|
| wacky.bmp | 4,302 bytes - Ratio 214.24 | 3,951 bytes - Ratio 233.27 | 3,951 bytes - Ratio 233.27 | 3,952 bytes - Ratio Ratio 233.27 |
| winnt256.bmp | 159,050 bytes - Ratio 0.987 | 62,931 bytes - Ratio 2.50 | 62,931 bytes - Ratio 2.50 | 62,931 bytes - Ratio 2.50 |