package pick.com.app.base

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.webview_layout.*




class PdfViewerActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(pick.com.app.R.layout.webview_layout)



        val webSettings = tw__web_view.getSettings()
        webSettings.setJavaScriptEnabled(true)

        val webViewClient = WebViewClientImpl(this)
        tw__web_view.setWebViewClient(webViewClient)

        tw__web_view.loadUrl("http://www.axmag.com/download/pdfurl-guide.pdf")


    }

        inner class WebViewClientImpl(activity: Activity) : WebViewClient() {

            private var activity: Activity? = null

            init {
                this.activity = activity
            }

            override fun shouldOverrideUrlLoading(webView: WebView, url: String): Boolean {
                if (url.indexOf("journaldev.com") > -1) return false

                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                activity!!.startActivity(intent)
                return true
            }

        }
}
