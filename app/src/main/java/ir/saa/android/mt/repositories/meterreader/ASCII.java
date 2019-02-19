package ir.saa.android.mt.repositories.meterreader;

public class ASCII {
    public static final char NUL = 0x00; // null character
    public static final char SOH = 0x01; // start of heading
    public static final char STX = 0x02; // start of text
    public static final char ETX = 0x03; // end of text
    public static final char EOT = 0x04; // end of transmission

    public static final char noChar = 0x20; // null character
    public static   char BCC = 0x00; // end of transmission

    public static final char ACK = 0x06; // acknowledgment
    public static final char NAK = 0x15; // negative acknowledgment

    public static final char LF = 0x0A; // NL line feed, new page
    public static final char CR = 0x0D; // carriage return

    public static final char STAR = 0x2a; //  * charachter
    public static final char DOT = 0x2e; //  . charachter
    public static final char K_CHAR = 0x6b; //  k charachter
    public static final char OPEN_PRNTZ = 0x28;
    public static final char CLOSE_PRNTZ = 0x29;
}
