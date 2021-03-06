package ohi.andre.consolelauncher.commands.main.raw;

import android.text.format.Time;

import ohi.andre.consolelauncher.R;
import ohi.andre.consolelauncher.commands.CommandAbstraction;
import ohi.andre.consolelauncher.commands.ExecutePack;
import ohi.andre.consolelauncher.managers.XMLPrefsManager;

/**
 * Created by andre on 03/12/15.
 */
public class time implements CommandAbstraction {
    @Override
    public String exec(ExecutePack pack) {
        Time time = new Time();
        time.setToNow();
        return time.format(XMLPrefsManager.get(String.class, XMLPrefsManager.Behavior.time_format));
    }

    @Override
    public int minArgs() {
        return 0;
    }

    @Override
    public int maxArgs() {
        return 0;
    }

    @Override
    public int priority() {
        return 1;
    }

    @Override
    public int[] argType() {
        return new int[0];
    }

    @Override
    public int helpRes() {
        return R.string.help_time;
    }

    @Override
    public String onArgNotFound(ExecutePack info, int index) {
        return null;
    }

    @Override
    public String onNotArgEnough(ExecutePack info, int nArgs) {
        return null;
    }
}
