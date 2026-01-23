function addEvent(obj ,evt, fnc)
			{
			
			if (obj.addEventListener)
			obj.addEventListener(evt,fnc,false);
			else if (obj.attachEvent)
			obj.attachEvent('on'+evt,fnc);
			else

			return true;
			}

      function removeEvent(obj ,evt, fnc)
      {
        if (obj.removeEventListener)
          obj.removeEventListener(evt,fnc,false);
        else if (obj.detachEvent)
          obj.detachEvent('on'+evt,fnc);
		
        else
          return false;
        return true;
      }

      //----------

      function appendElement(node,tag,id,htm)
      {
        var ne = document.createElement(tag);
		
        if(id) ne.id = id;
        if(htm) ne.innerHTML = htm;
        node.appendChild(ne);
      }

      //----------

      function showPopup(p)
      {
        greyout(true);
        document.getElementById(p).style.display = 'block';
      }

      function hidePopup(p)
      {
        greyout(false);
        document.getElementById(p).style.display = 'none';
      }

      //----------

      function greyout(d)
      {
	    var obj = document.getElementById('greyout');
		if(!obj)
        {
          appendElement(document.body,'div','greyout');
		  obj = document.getElementById('greyout');
		  obj.style.position = 'absolute';
          obj.style.top = '0px';
          obj.style.left = '0px';
          obj.style.background = 'rgba(0, 0, 0, 0.85098)';
          obj.style.opacity = '.9';
		  obj.style.zIndex = '3';
          obj.style.filter = 'blur(opacity=20)';
        }
        if(d)
        {
			var ch = document.documentElement.clientHeight ? document.documentElement.clientHeight : document.body.clientHeight;
			var cw = document.documentElement.clientWidth ? document.documentElement.clientWidth : document.body.clientWidth;
			var sh = document.documentElement.scrollHeight ? document.documentElement.scrollHeight : document.body.scrollHeight;
			
			if(document.body.scrollHeight) sh = Math.max(sh,document.body.scrollHeight)
			var sw = document.documentElement.scrollWidth ? document.documentElement.scrollWidth : document.body.scrollWidth;
			
			if(document.body.scrollWidth) sw = Math.max(sw,document.body.scrollWidth)
			var wh = window.innerHeight ? window.innerHeight : document.body.offsetHeight;
			
			obj.style.height = Math.max(wh,Math.max(sh,ch))+'px';
			obj.style.width  = Math.max(sw,cw)+'px';
			obj.style.display = 'block';
			addEvent(window,'resize',greyoutResize);
			chectbrowser('hidden');
			
				
        }
        else
        {
          obj.style.display = 'none';   
          removeEvent(window,'resize',greyoutResize);
		  chectbrowser('visible');
		
        }
      }

      function greyoutResize()
      {
		 
        var ch = document.documentElement.clientHeight ? document.documentElement.clientHeight : document.body.clientHeight;
	    var cw = document.documentElement.clientWidth ? document.documentElement.clientWidth : document.body.clientWidth;
	    var sh = document.documentElement.scrollHeight ? document.documentElement.scrollHeight : document.body.scrollHeight;
		if(document.body.scrollHeight) sh = Math.max(sh,document.body.scrollHeight)
        var sw = document.documentElement.scrollWidth ? document.documentElement.scrollWidth : document.body.scrollWidth;
		if(document.body.scrollWidth) sw = Math.max(sw,document.body.scrollWidth)
        var wh = window.innerHeight ? window.innerHeight : document.body.offsetHeight;
        var obj = document.getElementById('greyout');
        obj.style.height = ch+'px';
		obj.style.width  = cw+'px';
		obj.style.height = Math.max(wh,Math.max(sh,ch))+'px';
		obj.style.width  = Math.max(sw,cw)+'px';
	  }



function showHideElements(tagName,desc) 
{
var els = document.getElementsByTagName(tagName);
for (var i = 0; i < els.length; i++)
{
els[i].style.visibility = desc;

}
}

function chectbrowser(desc)
{
	if (/MSIE (\d+\.\d+);/.test(navigator.userAgent))
	{
		var ieversion=new Number(RegExp.$1) 
		if (ieversion <= 6)
		{
			showHideElements('SELECT',desc) 
		}
	}
}