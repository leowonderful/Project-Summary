/**
 * An implementation of ExpansionCodeBookInterface using an array.
 */

public class ArrayCodeBook implements ExpansionCodeBookInterface {
    private static final int R = 256;        // alphabet size
    private String[] codebook;
    private int W;       // current codeword width
    private int minW;    // minimum codeword width
    private int maxW;    // maximum codeword width
    private int L;       // maximum number of codewords with 
                         // current codeword width (L = 2^W)
    private int code;    // next available codeword value
    private boolean changed = false;
    private boolean justFlushed = false;
  
    public ArrayCodeBook(int minW, int maxW){
        this.maxW = maxW;
        this.minW = minW;
        initialize();    
    }
    public int size(){
        return code;
    }

    //TODO: Return the effective width of the codeword
    //Decompression is one step behind compression
    //After we increment width, as we are one step behind
    //The next add effective width is W-1
    //All others return W
    //So this is called 7 times
    

    public int getCodewordWidth(boolean flushIfFull){ 
    	if(L == Math.pow(2,  maxW)) return minW;
    	if(L == Math.pow(2,  W)) return W+1;
    	return W;
    }
    
    private void initialize(){
        codebook = new String[1 << maxW];
        W = minW;
        L = 1<<W;
        code = 0;
        // initialize symbol table with all 1-character strings
        for (int i = 0; i < R; i++)
            add("" + (char) i, false);
        add("", false); //R is codeword for EOF
    }

    public void add(String str, boolean flushIfFull) {
    	changed = false;
    	justFlushed = false;
        boolean haveRoom = false;
        if(code < L){
            haveRoom = true;
        }
        //If we have room, add to the codebook.
        if(haveRoom){
            codebook[code] = str;
            code++;
          
            System.err.println("Str: " + str + " W:" + W);
        }
        
        // If no Codewords remain and we are not at max W, then increment Width
        if(!haveRoom && W < maxW) {
        	W++;
        	L*=2;
        	
        	
        	//System.err.println("Str: " + str + " W:" + W);
        }
        //If no Codewords remain and we are at max W, then reset Codebook
        else if(!haveRoom && W >= maxW && flushIfFull) {
        	this.initialize();
        	justFlushed = true;
        	
        	
        	//System.err.println("Str: " + str + " W:" + W);
        }
    }

    public String getString(int codeword) {
    	System.err.println("codeword" + codeword);
        return codebook[codeword];
    }
    
}