package it.neokree.hplusfitprotocol;

public class Framer {
  public static final byte STDIN_FRAME_PREFIX = '-';
  public static final byte STDIN_REQUEST_FRAME_PREFIX = '_';
  public static final byte STDOUT_FRAME_PREFIX = '1';
  public static final byte STDERR_FRAME_PREFIX = '2';
}
