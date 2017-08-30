package rocks.teagantotally.ibotta_challenge.events.notifications;

import android.support.annotation.NonNull;

/**
 * Created by tglenn on 8/30/17.
 */

public class ProgressDialogNotificationEvent
          extends NotificationEvent {
    ProgressDialogNotificationEvent(@NonNull String message) {
        super(message);
    }
}
