package xyz.luan.monstros.domain.user;

import io.yawp.commons.http.annotation.GET;
import io.yawp.repository.actions.Action;
import xyz.luan.monstros.ws.AuthHolder;

import java.util.Map;

public class UserMeAction extends Action<User> {

    @GET
    public Map<String, Object> me() {
        Map<String, Object> me = asMap(AuthHolder.user.get());
        me.put("company", AuthHolder.company.get());
        return me;
    }
}
