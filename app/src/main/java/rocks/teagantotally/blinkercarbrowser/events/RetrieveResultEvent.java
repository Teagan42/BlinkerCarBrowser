package rocks.teagantotally.blinkercarbrowser.events;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.Objects;

/**
 * Created by tglenn on 9/14/17.
 */

public abstract class RetrieveResultEvent<RetrieveEvent extends BaseEvent> {
    public enum Result {
        SUCCESS,
        ERROR
    }

    private RetrieveEvent retrieveEvent;
    private Result result;
    private String errorReason;

    /**
     * Create a new retrieve result event
     *
     * @param retrieveEvent The event that triggered the data retrieve
     * @param result        Result of the retrieve event
     * @param errorReason   The reason for the error
     */
    protected RetrieveResultEvent(@NonNull RetrieveEvent retrieveEvent,
                                  @NonNull Result result,
                                  @Nullable String errorReason) {
        Objects.requireNonNull(retrieveEvent,
                               "Retrieve event cannot be null");
        Objects.requireNonNull(result,
                               "Result cannot be null");
        if (result == Result.ERROR && TextUtils.isEmpty(errorReason)) {
            throw new IllegalArgumentException("Error reason must be set");
        }

        this.retrieveEvent = retrieveEvent;
        this.result = result;
        this.errorReason = errorReason;
    }

    /**
     * @return The event that triggered the retrieval
     */
    public RetrieveEvent getRetrieveEvent() {
        return retrieveEvent;
    }

    /**
     * @return The result of the retrieve request
     */
    public Result getResult() {
        return result;
    }

    /**
     * @return The error reason text
     */
    public String getErrorReason() {
        return errorReason;
    }
}
