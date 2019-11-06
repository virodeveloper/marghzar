package pick.com.app.varient.user.ui.fragment.booking;

import android.app.Activity;
import android.app.VoiceInteractor;
import android.os.Build;
import android.os.Bundle;
import android.view.ViewGroup;
import androidx.annotation.RequiresApi;
import pick.com.app.R;

public class dsfds  extends Activity {

    @RequiresApi(api = Build.VERSION_CODES.M)
    class Confirm extends VoiceInteractor.ConfirmationRequest {
        public Confirm(String ttsPrompt, String visualPrompt) {
            super(new VoiceInteractor.Prompt(
                    new String[] {ttsPrompt}, visualPrompt), null);


        }

        @Override
        public void onConfirmationResult(boolean confirmed, Bundle result) {
            super.onConfirmationResult(confirmed, result);
            if (confirmed) {
                //doAction();
            }
            finish();

        }
    };


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onResume() {
        super.onResume();




        if (isVoiceInteraction()) {
            Bundle status = new Bundle();
            VoiceInteractor.Request request = null;


                request = new VoiceInteractor.CompleteVoiceRequest(new VoiceInteractor.Prompt(
                        new String[] {"hello missa"}, "hello missa"), status);

            getVoiceInteractor().submitRequest(request);
        }
        finish();
    }

}
