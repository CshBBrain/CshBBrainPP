CshBBrainPP
===========

Open Source Picture Server based on CshBBrain net frame.
CshBBrainPP 是一款基于JAVA的开源图片服务器，只提供上传图片和下载图片服务；基于NIO网络架构，和开源WebSocket服务器 宝贝鱼(CshBBrain)是同门兄弟，基于相同的网络架构，采用相同的分层结构。
CshBBrainPP 的目的是要解决大量并发访问大量小图片时，磁盘读取非常频繁，而且响应速度很慢的问题。目前将所有图片全部缓存到内存中，不适合海量图片的网站，但也许适合你的网站呢；后续会改进为选择性的将一些热点图片进行缓存，非热点图片不缓存。当前只开发了
下载图片的功能，后续将开发上传图片的功能，使之成为独立的图片服务器。
下图是缓存到内存和不缓存到内存请求图片时，花销在网络连接、磁盘读取的时间对比图，一图解真相。
<img src="http://dl.iteye.com/upload/attachment/0080/2931/b793766e-448b-3252-b275-cbafdaf2040b.png"/>
