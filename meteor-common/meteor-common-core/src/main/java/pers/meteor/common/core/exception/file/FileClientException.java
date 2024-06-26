package pers.meteor.common.core.exception.file;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * @author meteor
 */
public class FileClientException extends Exception {

    private static final long serialVersionUID = 1L;

    private final Throwable cause;

    public FileClientException()
    {
        this(null, null);
    }

    public FileClientException(final String msg)
    {
        this(msg, null);
    }

    public FileClientException(String msg, Throwable cause)
    {
        super(msg);
        this.cause = cause;
    }

    @Override
    public void printStackTrace(PrintStream stream)
    {
        super.printStackTrace(stream);
        if (cause != null)
        {
            stream.println("Caused by:");
            cause.printStackTrace(stream);
        }
    }

    @Override
    public void printStackTrace(PrintWriter writer)
    {
        super.printStackTrace(writer);
        if (cause != null)
        {
            writer.println("Caused by:");
            cause.printStackTrace(writer);
        }
    }
}
