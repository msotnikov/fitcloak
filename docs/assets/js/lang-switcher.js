(function() {
  var LANGS = ['ar','de','es','fr','hi','it','ja','ko','pt','ru','tr','zh'];
  var NAV = {
    en: { index:'Home', 'quick-start':'Quick Start', configuration:'Configuration', cli:'CLI Options', 'vite-integration':'Vite / HMR Integration', 'theme-development':'Theme Development', troubleshooting:'Troubleshooting', contributing:'Contributing' },
    ar: { index:'الرئيسية', 'quick-start':'البدء السريع', configuration:'الإعدادات', cli:'خيارات سطر الأوامر', 'vite-integration':'تكامل Vite / HMR', 'theme-development':'تطوير القوالب', troubleshooting:'استكشاف الأخطاء', contributing:'المساهمة' },
    de: { index:'Startseite', 'quick-start':'Schnellstart', configuration:'Konfiguration', cli:'CLI-Optionen', 'vite-integration':'Vite / HMR-Integration', 'theme-development':'Theme-Entwicklung', troubleshooting:'Fehlerbehebung', contributing:'Mitwirken' },
    es: { index:'Inicio', 'quick-start':'Inicio Rápido', configuration:'Configuración', cli:'Opciones de CLI', 'vite-integration':'Integración Vite / HMR', 'theme-development':'Desarrollo de Temas', troubleshooting:'Solución de Problemas', contributing:'Contribuir' },
    fr: { index:'Accueil', 'quick-start':'Démarrage rapide', configuration:'Configuration', cli:'Options CLI', 'vite-integration':'Intégration Vite / HMR', 'theme-development':'Développement de thèmes', troubleshooting:'Dépannage', contributing:'Contribuer' },
    hi: { index:'मुखपृष्ठ', 'quick-start':'त्वरित शुरुआत', configuration:'कॉन्फ़िगरेशन', cli:'CLI विकल्प', 'vite-integration':'Vite / HMR इंटीग्रेशन', 'theme-development':'थीम विकास', troubleshooting:'समस्या निवारण', contributing:'योगदान' },
    it: { index:'Home', 'quick-start':'Guida Rapida', configuration:'Configurazione', cli:'Opzioni CLI', 'vite-integration':'Integrazione Vite / HMR', 'theme-development':'Sviluppo Temi', troubleshooting:'Risoluzione dei Problemi', contributing:'Contribuire' },
    ja: { index:'ホーム', 'quick-start':'クイックスタート', configuration:'設定', cli:'CLI オプション', 'vite-integration':'Vite / HMR 統合', 'theme-development':'テーマ開発', troubleshooting:'トラブルシューティング', contributing:'コントリビュート' },
    ko: { index:'홈', 'quick-start':'빠른 시작', configuration:'설정', cli:'CLI 옵션', 'vite-integration':'Vite / HMR 통합', 'theme-development':'테마 개발', troubleshooting:'문제 해결', contributing:'기여하기' },
    pt: { index:'Início', 'quick-start':'Início Rápido', configuration:'Configuração', cli:'Opções de CLI', 'vite-integration':'Integração Vite / HMR', 'theme-development':'Desenvolvimento de Temas', troubleshooting:'Solução de Problemas', contributing:'Contribuir' },
    ru: { index:'Главная', 'quick-start':'Быстрый старт', configuration:'Конфигурация', cli:'Параметры командной строки', 'vite-integration':'Интеграция с Vite / HMR', 'theme-development':'Разработка тем', troubleshooting:'Устранение неполадок', contributing:'Участие в проекте' },
    tr: { index:'Ana Sayfa', 'quick-start':'Hızlı Başlangıç', configuration:'Yapılandırma', cli:'CLI Seçenekleri', 'vite-integration':'Vite / HMR Entegrasyonu', 'theme-development':'Tema Geliştirme', troubleshooting:'Sorun Giderme', contributing:'Katkı Sağlama' },
    zh: { index:'首页', 'quick-start':'快速开始', configuration:'配置', cli:'CLI 选项', 'vite-integration':'Vite / HMR 集成', 'theme-development':'主题开发', troubleshooting:'故障排除', contributing:'贡献' }
  };

  function detectLang() {
    var parts = location.pathname.split('/').filter(Boolean);
    if (parts.length > 0 && LANGS.indexOf(parts[0]) !== -1) return parts[0];
    return 'en';
  }

  function pageSlug(href) {
    var parts = href.replace(/^\//, '').split('/').filter(Boolean);
    if (parts.length > 0 && LANGS.indexOf(parts[0]) !== -1) parts.shift();
    var last = parts.pop() || 'index';
    last = last.replace(/\.(html|md)$/, '');
    if (last === '' || last === 'index') last = 'index';
    return last;
  }

  document.addEventListener('DOMContentLoaded', function() {
    var lang = detectLang();

    var sel = document.getElementById('lang-select');
    if (sel) {
      sel.addEventListener('change', function() {
        var newLang = this.value;
        var path = location.pathname;
        var parts = path.split('/').filter(Boolean);
        if (parts.length > 0 && LANGS.indexOf(parts[0]) !== -1) parts.shift();
        var page = parts.join('/');
        if (newLang === 'en') {
          location.href = '/' + page;
        } else {
          location.href = '/' + newLang + '/' + page;
        }
      });
    }

    if (lang === 'en') return;
    var titles = NAV[lang];
    if (!titles) return;

    var links = document.querySelectorAll('.site-nav a.nav-list-link, .site-nav a');
    links.forEach(function(a) {
      var slug = pageSlug(a.getAttribute('href') || '');
      var translated = titles[slug];
      if (translated) {
        var origHref = a.getAttribute('href');
        var ext = '';
        var m = origHref.match(/\.(html|md)$/);
        if (m) ext = '.' + m[1];
        if (slug === 'index') {
          a.setAttribute('href', '/' + lang + '/' + (ext ? 'index' + ext : ''));
        } else {
          a.setAttribute('href', '/' + lang + '/' + slug + ext);
        }
        a.textContent = translated;
      }
    });

    var titleLink = document.querySelector('.site-title');
    if (titleLink && titles['index']) {
      titleLink.setAttribute('href', '/' + lang + '/');
    }
  });
})();
