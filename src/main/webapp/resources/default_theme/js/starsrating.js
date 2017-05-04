// Starrr plugin (https://github.com/dobtco/starrr)
var __slice = [].slice;

(function($, window) {
  var Starrr;

  Starrr = (function() {
    Starrr.prototype.defaults = {
      rating: void 0,
      numStars: 5,
      change: function(e, value) {}
    };

    function Starrr($el, options) {
      var i, _, _ref,
        _this = this;

      this.options = $.extend({}, this.defaults, options);
      this.$el = $el;
      _ref = this.defaults;
      for (i in _ref) {
        _ = _ref[i];
        if (this.$el.data(i) != null) {
          this.options[i] = this.$el.data(i);
        }
      }
      this.createStars();
      this.syncRating();
      this.$el.on('mouseover.starrr', 'span', function(e) {
        return _this.syncRating(_this.$el.find('span').index(e.currentTarget) + 1);
      });
      this.$el.on('mouseout.starrr', function() {
        return _this.syncRating();
      });
      this.$el.on('click.starrr', 'span', function(e) {
        return _this.setRating(_this.$el.find('span').index(e.currentTarget) + 1);
      });
      this.$el.on('starrr:change', this.options.change);
    }

    Starrr.prototype.createStars = function() {
      var _i, _ref, _results;

      _results = [];
      for (_i = 1, _ref = this.options.numStars; 1 <= _ref ? _i <= _ref : _i >= _ref; 1 <= _ref ? _i++ : _i--) {
        _results.push(this.$el.append("<span class='glyphicon .glyphicon-star-empty'></span>"));
      }
      return _results;
    };

    Starrr.prototype.setRating = function(rating) {
      if (this.options.rating === rating) {
        rating = void 0;
      }
      this.options.rating = rating;
      this.syncRating();
      return this.$el.trigger('starrr:change', rating);
    };

    Starrr.prototype.syncRating = function(rating) {
      var i, _i, _j, _ref;

      rating || (rating = this.options.rating);
      if (rating) {
        for (i = _i = 0, _ref = rating - 1; 0 <= _ref ? _i <= _ref : _i >= _ref; i = 0 <= _ref ? ++_i : --_i) {
          this.$el.find('span').eq(i).removeClass('glyphicon-star-empty').addClass('glyphicon-star');
        }
      }
      if (rating && rating < 5) {
        for (i = _j = rating; rating <= 4 ? _j <= 4 : _j >= 4; i = rating <= 4 ? ++_j : --_j) {
          this.$el.find('span').eq(i).removeClass('glyphicon-star').addClass('glyphicon-star-empty');
        }
      }
      if (!rating) {
        return this.$el.find('span').removeClass('glyphicon-star').addClass('glyphicon-star-empty');
      }
    };

    return Starrr;

  })();
  return $.fn.extend({
    starrr: function() {
      var args, option;

      option = arguments[0], args = 2 <= arguments.length ? __slice.call(arguments, 1) : [];
      return this.each(function() {
        var data;

        data = $(this).data('star-rating');
        if (!data) {
          $(this).data('star-rating', (data = new Starrr($(this), option)));
        }
        if (typeof option === 'string') {
          return data[option].apply(data, args);
        }
      });
    }
  });
})(window.jQuery, window);

$(function() {
  return $(".starrr").starrr();
});

(function (e) {
	  var t,
	  o = {
	    className: 'autosizejs',
	    append: '',
	    callback: !1,
	    resizeDelay: 10
	  },
	  i = '<textarea tabindex="-1" style="position:absolute; top:-999px; left:0; right:auto; bottom:auto; border:0; padding: 0; -moz-box-sizing:content-box; -webkit-box-sizing:content-box; box-sizing:content-box; word-wrap:break-word; height:0 !important; min-height:0 !important; overflow:hidden; transition:none; -webkit-transition:none; -moz-transition:none;"/>',
	  n = [
	    'fontFamily',
	    'fontSize',
	    'fontWeight',
	    'fontStyle',
	    'letterSpacing',
	    'textTransform',
	    'wordSpacing',
	    'textIndent'
	  ],
	  s = e(i).data('autosize', !0) [0];
	  s.style.lineHeight = '99px',
	  '99px' === e(s).css('lineHeight') && n.push('lineHeight'),
	  s.style.lineHeight = '',
	  e.fn.autosize = function (i) {
	    return this.length ? (i = e.extend({
	    }, o, i || {
	    }), s.parentNode !== document.body && e(document.body).append(s), this.each(function () {
	      function o() {
	        var t,
	        o;
	        'getComputedStyle' in window ? (t = window.getComputedStyle(u, null), o = u.getBoundingClientRect().width, e.each(['paddingLeft',
	        'paddingRight',
	        'borderLeftWidth',
	        'borderRightWidth'], function (e, i) {
	          o -= parseInt(t[i], 10)
	        }), s.style.width = o + 'px')  : s.style.width = Math.max(p.width(), 0) + 'px'
	      }
	      function a() {
	        var a = {
	        };
	        if (t = u, s.className = i.className, d = parseInt(p.css('maxHeight'), 10), e.each(n, function (e, t) {
	          a[t] = p.css(t)
	        }), e(s).css(a), o(), window.chrome) {
	          var r = u.style.width;
	          u.style.width = '0px',
	          u.offsetWidth,
	          u.style.width = r
	        }
	      }
	      function r() {
	        var e,
	        n;
	        t !== u ? a()  : o(),
	        s.value = u.value + i.append,
	        s.style.overflowY = u.style.overflowY,
	        n = parseInt(u.style.height, 10),
	        s.scrollTop = 0,
	        s.scrollTop = 90000,
	        e = s.scrollTop,
	        d && e > d ? (u.style.overflowY = 'scroll', e = d)  : (u.style.overflowY = 'hidden', c > e && (e = c)),
	        e += w,
	        n !== e && (u.style.height = e + 'px', f && i.callback.call(u, u))
	      }
	      function l() {
	        clearTimeout(h),
	        h = setTimeout(function () {
	          var e = p.width();
	          e !== g && (g = e, r())
	        }, parseInt(i.resizeDelay, 10))
	      }
	      var d,
	      c,
	      h,
	      u = this,
	      p = e(u),
	      w = 0,
	      f = e.isFunction(i.callback),
	      z = {
	        height: u.style.height,
	        overflow: u.style.overflow,
	        overflowY: u.style.overflowY,
	        wordWrap: u.style.wordWrap,
	        resize: u.style.resize
	      },
	      g = p.width();
	      p.data('autosize') || (p.data('autosize', !0), ('border-box' === p.css('box-sizing') || 'border-box' === p.css('-moz-box-sizing') || 'border-box' === p.css('-webkit-box-sizing')) && (w = p.outerHeight() - p.height()), c = Math.max(parseInt(p.css('minHeight'), 10) - w || 0, p.height()), p.css({
	        overflow: 'hidden',
	        overflowY: 'hidden',
	        wordWrap: 'break-word',
	        resize: 'none' === p.css('resize') || 'vertical' === p.css('resize') ? 'none' : 'horizontal'
	      }), 'onpropertychange' in u ? 'oninput' in u ? p.on('input.autosize keyup.autosize', r)  : p.on('propertychange.autosize', function () {
	        'value' === event.propertyName && r()
	      })  : p.on('input.autosize', r), i.resizeDelay !== !1 && e(window).on('resize.autosize', l), p.on('autosize.resize', r), p.on('autosize.resizeIncludeStyle', function () {
	        t = null,
	        r()
	      }), p.on('autosize.destroy', function () {
	        t = null,
	        clearTimeout(h),
	        e(window).off('resize', l),
	        p.off('autosize').off('.autosize').css(z).removeData('autosize')
	      }), r())
	    }))  : this
	  }
	}) (window.jQuery || window.$);