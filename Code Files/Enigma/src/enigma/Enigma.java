package enigma;

/**
 *
 * @author Apurv Jain
 */
public class Enigma
{
    private Rotor rightRotor;   //Right Rotor
    private Rotor middleRotor;  //Middle Rotor
    private Rotor leftRotor;    //Right Rotor
    private Reflector reflector; //Reflector
    private int[] plugboard;     //Plugboard
    public static final String[] I;    //Rotor I
    public static final String[] II;   //Rotor II
    public static final String[] III;  //Rotor III
    public static final String[] IV;   //Rotor IV
    public static final String[] V;    //Rotor V
    public static final String A = "EJMZALYXVBWFCRQUONTSPIKHGD"; //Reflector A
    public static final String B = "YRUHQSLDPXNGOKMIEBFZCWVJAT"; //Reflector B   
    public static final String C = "FVPJIAOYEDRZXWGCTKUQSBNMHL"; //Reflector C
    
    static {
        I = new String[] { "EKMFLGDQVZNTOWYHXUSPAIBRCJ", "Q" };  //Rotor Sequence with Notch
        II = new String[] { "AJDKSIRUXBLHWTMCQGZNPYFVOE", "E" };
        III = new String[] { "BDFHJLCPRTXVZNYEIWGAKMUSQO", "V" };
        IV = new String[] { "ESOVPZJAYQUIRHXLNFTGKDCMWB", "J" };
        V = new String[] { "VZBRGITYUPSDNHLXAWMJQOFECK", "Z" };
    }
    
    public Enigma(final String[] left, final String[] middle, final String[] right, final String ref) {
        //Checking for correct Rotor
        if (!this.correctRotor(left) || !this.correctRotor(middle) || !this.correctRotor(right)) {
            throw new RuntimeException("Please choose a correct Rotor");
        }
        //Checking for correct Reflector
        if (!this.correctReflector(ref)) {
            throw new RuntimeException("Please choose a correct Reflector");
        }
        this.leftRotor = new Rotor(left[0], left[1].charAt(0)); //Initializing Left Rotor
        this.middleRotor = new Rotor(middle[0], middle[1].charAt(0));   //Initializing Middle Rotor
        this.rightRotor = new Rotor(right[0], right[1].charAt(0));  //Initializing Right Rotor
        this.reflector = new Reflector(ref); //Initializing Reflector
        this.plugboard = new int[26];   //Plugboard
        this.resetPlugboard();          //Reserring the Plugboard to Initial (default) setting
    }
    
    //Checking for correct Rotor
    private boolean correctRotor(final String[] rotor) {
        return rotor == Enigma.I || rotor == Enigma.II || rotor == Enigma.III || rotor == Enigma.IV || rotor == Enigma.V;
    }
    
    //Checking for correct Reflector
    private boolean correctReflector(final String reflector) {
        return reflector == "EJMZALYXVBWFCRQUONTSPIKHGD" || reflector == "YRUHQSLDPXNGOKMIEBFZCWVJAT" || reflector == "FVPJIAOYEDRZXWGCTKUQSBNMHL";
    }
    
    //For displaying Output
    //Only Upper Case letters are allowed
    //Space and New Line character are printed as it is
    public String type(final String text) {
        String output = "";
        for (int i = 0; i < text.length(); ++i) {
            if (text.charAt(i) >= 'A' && text.charAt(i) <= 'Z') {
                output = String.valueOf(output) + this.rotorsEncryption(text.charAt(i));
            }
            else {
                if (text.charAt(i) != ' ' && text.charAt(i) != '\n') {
                    throw new RuntimeException("Only upper case letters allowed!");
                }
                output = String.valueOf(output) + text.charAt(i);
            }
        }
        return output;
    }
    
    //For encrypting the Input Text
    private char rotorsEncryption(final char inputC) {
        if (this.middleRotor.getNotch() == this.middleRotor.getRotorHead()) {
            this.leftRotor.rotate();
            this.middleRotor.rotate(); //Double Stepping
        }
        if (this.rightRotor.getNotch() == this.rightRotor.getRotorHead()) {
            this.middleRotor.rotate();
        }
        this.rightRotor.rotate();
        int input = inputC - 'A';
        //If Plugboard setting set for this alphabet, then take as input to right rotor
        //Otherwise take the alphabet as it is
        if (this.plugboard[input] != -1) {
            input = this.plugboard[input];
        }
        final int outOfrightRotor = this.rightRotor.getOutputOf(input); //Getting output from right rotor for the input
        final int outOfmiddleRotor = this.middleRotor.getOutputOf(outOfrightRotor); //Getting output from middle rotor for the right rotor
        final int outOfleftRotor = this.leftRotor.getOutputOf(outOfmiddleRotor); //Getting output from left rotor for the middle rotor
        final int outOfReflector = this.reflector.getOutputOf(outOfleftRotor); //Getting output of Reflector
        final int inOfleftRotor = this.leftRotor.getInputOf(outOfReflector); //Getting output from right rotor for the reflector
        final int inOfmiddleRotor = this.middleRotor.getInputOf(inOfleftRotor); //Getting output from the middle rotor for the right rotor
        int inOfrightRotor = this.rightRotor.getInputOf(inOfmiddleRotor); //Getting output from the left rotor for the middle rotor
        //If Plugboard setting set for this alphabet output, then take as output
        //Otherwise take the output alphabet as it is 
        if (this.plugboard[inOfrightRotor] != -1) { 
            inOfrightRotor = this.plugboard[inOfrightRotor];
        }
        return (char)(inOfrightRotor + 65);
    }
    
    //Getting Left Rotor
    public Rotor getLeftRotor() {
        return this.leftRotor;
    }
    
    //Getting Middle Rotor
    public Rotor getMiddleRotor() {
        return this.middleRotor;
    }
    
    //Getting Right Rotor
    public Rotor getRightRotor() {
        return this.rightRotor;
    }
    
    //Getting Reflector
    public Reflector getReflector() {
        return this.reflector;
    }
    
    //Setting the Plugboard
    public void insertPlugboardWire(final char a, final char b) {
        this.plugboard[a - 'A'] = b - 'A';
        this.plugboard[b - 'A'] = a - 'A';
    }
    
    //Removing the Plugboard Settings
    public void removePlugboardWire(final char a) {
        this.plugboard[this.plugboard[a - 'A']] = -1;
        this.plugboard[a - 'A'] = -1;
    }
    
    //Getting Plugboard setting for an Alphabet
    public int getPlugboardOf(final int a) {
        return this.plugboard[a];
    }
    
    //Resetting the Plugboard to Default setting
    public void resetPlugboard() {
        for (int wire = 0; wire < 26; ++wire) {
            this.plugboard[wire] = -1;
        }
    }
    
    //For checking if the Plugboard setting for the alphabet is set
    public boolean isPlugged(final char c) {
        return this.plugboard[c - 'A'] != -1;
    }
    
    //Resetting the Rotors
    public void resetRotation() {
        this.leftRotor.reset();
        this.middleRotor.reset();
        this.rightRotor.reset();
    }
}
