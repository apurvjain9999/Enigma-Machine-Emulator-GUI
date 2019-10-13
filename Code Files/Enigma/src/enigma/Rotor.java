package enigma;

/**
 *
 * @author Apurv Jain
 */

public class Rotor
{
    private int[] rotorOut;
    private int[] rotorIn;
    private int rotorHead;
    private int ringHead;
    private char notch;
    private int rotate;
    
    //Initializing the Rotor object
    protected Rotor(final String rotor, final char notch) {
        this.rotorOut = new int[26]; //Declaring array of Rotor Output
        this.rotorIn = new int[26];  //Declaring array of Rotor Input
        this.setRotor(new String[] { rotor, new StringBuilder(String.valueOf(notch)).toString() }); //Initializing the Rotor
    }
    
    //Getting output of Rotor
    protected int getOutputOf(final int pos) {
        final int rotorRingDiff = (this.rotorHead >= this.ringHead) ? (this.rotorHead - this.ringHead) : (26 - this.ringHead + this.rotorHead); //Calculating diff b/w Ring and Rotor Heads
        return (pos + this.rotorOut[(pos + this.rotate + rotorRingDiff) % 26]) % 26;
    }
    
    //Getting Input of the Rotor
    protected int getInputOf(final int pos) {
        final int rotorRingDiff = (this.rotorHead >= this.ringHead) ? (this.rotorHead - this.ringHead) : (26 - this.ringHead + this.rotorHead);
        final int posJump = pos - this.rotorIn[(pos + this.rotate + rotorRingDiff) % 26];
        return (posJump > 0) ? (posJump % 26) : ((26 + posJump) % 26);
    }
    
    //For getting the Notch
    public char getNotch() {
        return this.notch;
    }
    
    //For getting Rotor Head
    public char getRotorHead() {
        return (char)(65 + (this.rotorHead + this.rotate) % 26);
    }
    
    //For getting Ring Head of Rotor
    public char getRingHead() {
        return (char)(65 + (this.ringHead + this.rotate) % 26);
    }
    
    //For rotating the Rotor
    protected void rotate() {
        this.rotate = (this.rotate + 1) % 26;
    }
    
    //Setting the Rotor Head
    public void setRotorHead(final char c) {
        if (c < 'A' || c > 'Z') {
            throw new RuntimeException("Only upper case letters allowed!");
        }
        this.rotorHead = c - 'A';
        this.rotate = 0; //For counting the number of rotations
    }
    
    //Setting the Ring Head
    public void setRingHead(final char c) {
        if (c < 'A' || c > 'Z') {
            throw new RuntimeException("Only upper case letters allowed!");
        }
        this.ringHead = c - 'A';
    }
    
    //Setting the input and output Rotor
    public void setRotor(final String[] rotor) {
        //Setting the Notch
        this.notch = rotor[1].charAt(0);
        for (int i = 0; i < 26; ++i) {
            final int from = (char)(65 + i);
            final int to = rotor[0].charAt(i);
            this.rotorOut[i] = ((from < to) ? (to - from) : ((26 - (from - to)) % 26)); //Setting the Output 
            this.rotorIn[(i + this.rotorOut[i]) % 26] = this.rotorOut[i];   //Setting the Input: ABCDEFGHIJKLMNOPQRSTUVWXYZ 
        }
    }
    
    //Getting wire for Output
    public int getAnOutWire(final int pos) {
        return this.getOutputOf(pos);
    }
    
    //Getting Wire for Input
    public int getAnInWire(final int pos) {
        return this.getInputOf(pos);
    }
    
    //Resettiing the Rotor
    public void reset() {
        this.rotate = 0;
    }
}

