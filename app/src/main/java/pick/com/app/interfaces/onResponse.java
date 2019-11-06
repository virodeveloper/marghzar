package pick.com.app.interfaces;

public interface onResponse {

      <T> void   onSucess(T result,String methodtype);


    void onError(String error);
}
