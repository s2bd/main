import sys
import os
from PyQt5.QtWidgets import QApplication, QMainWindow, QAction, QToolBar
from PyQt5.QtWebEngineWidgets import QWebEngineView
from PyQt5.QtNetwork import QNetworkProxy

class MinimalTorBrowser(QMainWindow):
    def __init__(self):
        super().__init__()
        self.setWindowTitle("Minimal Tor Browser")
        self.setGeometry(100, 100, 800, 600)
        
        self.browser = QWebEngineView()
        self.browser.setUrl("https://duckduckgo.com")
        self.setCentralWidget(self.browser)
        
        self.tor_enabled = False
        
        toolbar = QToolBar()
        self.addToolBar(toolbar)
        
        toggle_tor_action = QAction("Toggle Tor", self)
        toggle_tor_action.triggered.connect(self.toggle_tor)
        toolbar.addAction(toggle_tor_action)
        
        back_action = QAction("Back", self)
        back_action.triggered.connect(self.browser.back)
        toolbar.addAction(back_action)
        
        forward_action = QAction("Forward", self)
        forward_action.triggered.connect(self.browser.forward)
        toolbar.addAction(forward_action)
        
        reload_action = QAction("Reload", self)
        reload_action.triggered.connect(self.browser.reload)
        toolbar.addAction(reload_action)
        
    def toggle_tor(self):
        if not self.tor_enabled:
            os.environ['http_proxy'] = 'socks5h://127.0.0.1:9050'
            os.environ['https_proxy'] = 'socks5h://127.0.0.1:9050'
            self.browser.setUrl(self.browser.url())  # Refresh
            print("Tor Enabled")
        else:
            os.environ.pop('http_proxy', None)
            os.environ.pop('https_proxy', None)
            self.browser.setUrl(self.browser.url())  # Refresh
            print("Tor Disabled")
        
        self.tor_enabled = not self.tor_enabled

if __name__ == "__main__":
    app = QApplication(sys.argv)
    window = MinimalTorBrowser()
    window.show()
    sys.exit(app.exec_())
