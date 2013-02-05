package com.jason.server.http;

/**
 * 
 * <li>类型名称：
 * <li>说明：请求头部信息
 * <li>创建人： 陈嗣洪
 * <li>创建日期：2011-11-22
 * <li>修改人： 
 * <li>修改日期：
 */
public final class HttpConstants{
	public static final String REQUEST = "request";// 请求
	public static final String REQUEST_IP = "ip";// 请求
	public static final String REQUEST_STREAM = "requestStream";// 请求
	/**
	 * 
	 * <li>类型名称：HttpHeaders
	 * <li>说明：Standard HTTP header names.
	 * <li>创建人： 陈嗣洪
	 * <li>创建日期：2011-11-22
	 * <li>修改人： 
	 * <li>修改日期：
	 */
    public static final class HeaderNames {
        public static final String ACCEPT = "Accept";// Accept
        public static final String ACCEPT_CHARSET = "Accept-Charset";// Accept-Charset
        public static final String ACCEPT_ENCODING= "Accept-Encoding";// Accept-Encoding
        public static final String ACCEPT_LANGUAGE = "Accept-Language";// Accept-Language
        public static final String ACCEPT_RANGES= "Accept-Ranges";// Accept-Ranges
        public static final String AGE = "Age";// Age
        public static final String ALLOW = "Allow";// Allow
        public static final String AUTHORIZATION = "Authorization";// Authorization
        public static final String CACHE_CONTROL = "Cache-Control";// Cache-Control
        public static final String CONNECTION = "Connection";// Connection
        public static final String CONTENT_ENCODING = "Content-Encoding";// Content-Encoding
        public static final String CONTENT_LANGUAGE= "Content-Language";// Content-Language
        public static final String CONTENT_LENGTH = "content-length";// Content-Length
        public static final String CONTENT_LOCATION = "Content-Location";// Content-Location
        public static final String CONTENT_TRANSFER_ENCODING = "Content-Transfer-Encoding";// Content-Transfer-Encoding
        public static final String CONTENT_MD5 = "Content-MD5";// Content-MD5
        public static final String CONTENT_RANGE = "Content-Range";// Content-Range
        public static final String CONTENT_TYPE= "content-type";// Content-Type
        public static final String UPLOAD_TYPE = "multipart/form-data";// 上传文件
        public static final String COOKIE = "Cookie";// Cookie
        public static final String DATE = "Date";// Date
        public static final String ETAG = "ETag";// ETag
        public static final String EXPECT = "Expect";// Expect
        public static final String EXPIRES = "Expires";// Expires
        public static final String FROM = "From";// From
        public static final String HOST = "Host";// Host
        public static final String IF_MATCH = "If-Match";// If-Match
        public static final String IF_MODIFIED_SINCE = "If-Modified-Since";// If-Modified-Since
        public static final String IF_NONE_MATCH = "If-None-Match";// If-None-Match
        public static final String IF_RANGE= "If-Range";// If-Range
        public static final String IF_UNMODIFIED_SINCE = "If-Unmodified-Since";// If-Unmodified-Since
        public static final String LAST_MODIFIED = "Last-Modified";// Last-Modified
        public static final String LOCATION = "Location";// Location
        public static final String MAX_FORWARDS = "Max-Forwards";// Max-Forwards
        public static final String PRAGMA = "Pragma";// Pragma
        public static final String PROXY_AUTHENTICATE = "Proxy-Authenticate";// Proxy-Authenticate
        public static final String PROXY_AUTHORIZATION = "Proxy-Authorization";// Proxy-Authorization
        public static final String RANGE = "Range";// Range
        public static final String REFERER = "Referer";// Referer
        public static final String RETRY_AFTER = "Retry-After";// Retry-After
        public static final String SERVER = "Server";// Server
        public static final String SET_COOKIE = "Set-Cookie";// Set-Cookie
        public static final String SET_COOKIE2 = "Set-Cookie2";// Set-Cookie2
        public static final String TE = "TE";// TE
        public static final String TRAILER = "Trailer";// Trailer
        public static final String TRANSFER_ENCODING = "Transfer-Encoding";// Transfer-Encoding
        public static final String UPGRADE = "Upgrade";// Upgrade
        public static final String USER_AGENT = "User-Agent";// User-Agent
        public static final String VARY = "Vary";// Vary
        public static final String VIA = "Via";// Via
        public static final String WARNING = "Warning";// Warning
        public static final String WWW_AUTHENTICATE = "WWW-Authenticate";// WWW-Authenticate
        private HeaderNames() {
            super();
        }
    }
    
    /**
     * 
     * <li>类型名称：HttpHeaders
     * <li>说明：Standard HTTP header values.
     * <li>创建人： 陈嗣洪
     * <li>创建日期：2011-11-22
     * <li>修改人： 
     * <li>修改日期：
     */
    public static final class HeaderValues {
        public static final String BASE64 = "base64";// base64
        public static final String BINARY = "binary";// binary
        public static final String BYTES = "bytes";// bytes
        public static final String CHARSET = "charset";// charset
        public static final String CHUNKED = "chunked";// chunked
        public static final String CLOSE = "close";// close
        public static final String COMPRESS = "compress";// compress
        public static final String CONTINUE =  "100-continue";// 100-continue
        public static final String DEFLATE = "deflate";// deflate
        public static final String GZIP = "gzip";// gzip
        public static final String IDENTITY = "identity";// identity
        public static final String KEEP_ALIVE = "keep-alive";// keep-alive
        public static final String MAX_AGE = "max-age";// max-age
        public static final String MAX_FRESH = "max-fresh";// max-fresh
        public static final String MAX_STALE = "max-stale";// max-stale
        public static final String MUST_REVALIDATE = "must-revalidate";// must-revalidate
        public static final String NO_CACHE = "no-cache";// no-cache
        public static final String NO_STORE = "no-store";// no-store
        public static final String NO_TRANSFORM = "no-transform";// no-transform
        public static final String NONE = "none";// none
        public static final String ONLY_IF_CACHED = "only-if-cached";// only-if-cached
        public static final String PRIVATE = "private";// private
        public static final String PROXY_REVALIDATE = "proxy-revalidate";// proxy-revalidate
        public static final String PUBLIC = "public";// public
        public static final String QUOTED_PRINTABLE = "quoted-printable";// quoted-printable
        public static final String S_MAXAGE = "s-maxage";// s-maxage       
        public static final String TRAILERS = "trailers";// trailers
        private HeaderValues() {
            super();
        }
    }
    
    /**
     * 
     * <li>类型名称：HttpHeaders
     * <li>说明：http协议支持的主要请求方法
     * <li>创建人： 陈嗣洪
     * <li>创建日期：2011-11-24
     * <li>修改人： 
     * <li>修改日期：
     */
    public static final class Methods{
    	public static final String GET = "GET";
    	public static final String HEAD = "HEAD";
    	public static final String POST = "POST";
    	public static final String PUT = "PUT";
    	public static final String TRACE = "TRACE";
    	
    	private Methods() {
            super();
        }
    	
    }
    
    /**
     * 
     * <li>类型名称：HttpHeaders
     * <li>说明：http服务器对请求的响应状态，目前只支持5中，其实只使用到200这个状态
     * <li>创建人： 陈嗣洪
     * <li>创建日期：2011-11-24
     * <li>修改人： 
     * <li>修改日期：
     */
    public static final class Status{
    	public static final Integer CONTINUE = 100;
    	public static final Integer OK = 200;
    	public static final Integer MULTIPLE_CHOICES = 300;
    	public static final Integer BAD_REQUEST = 400;
    	public static final Integer INTERNAL_SERVER_ERROR = 500;
    	
    	private Status() {
            super();
        }
    }
    
    /**
     * 
     * <li>类型名称：HttpHeaders
     * <li>说明：http协议中所使用到的协议保留字
     * <li>创建人： 陈嗣洪
     * <li>创建日期：2011-11-24
     * <li>修改人： 
     * <li>修改日期：
     */
    public static final class RetentionWord{
    	public static final byte SP = 32;// space ' '
    	public static final byte HT = 9;// tab ' '
    	public static final byte CR = 13;// Carriage return
    	public static final byte EQUALS = 61;// Equals '='
    	public static final byte LF = 10;// Line feed character
    	public static final byte[] CRLF = new byte[] { CR, LF };// carriage return line feed
    	public static final byte COLON = 58;// Colon ':'
    	public static final byte SEMICOLON = 59;// Semicolon ';'
    	public static final byte COMMA = 44;// comma ','
    	public static final byte DOUBLE_QUOTE = '"';
    	public static final String DEFAULT_CHARSET = "UTF-8";
    	public static final String CRLF_STR = "\r\n";// 回车换行
    	public static final String COLON_DOT = ":";//冒号
        
    	private RetentionWord() {
            super();
        }
    }

    private HttpConstants() {
        super();
    }
}
