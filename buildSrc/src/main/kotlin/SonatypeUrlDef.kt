object SonatypeUrlDef {

    // snapshots 对应view地址： https://oss.sonatype.org/content/repositories/snapshots/com/github/hanlyjiang/
    const val Snapshot_Upload_Url = "https://oss.sonatype.org/content/repositories/snapshots"
    const val Snapshot_Upload_Url_s01 = "https://s01.oss.sonatype.org/content/repositories/snapshots"

    // release 对应view地址：https://repo1.maven.org/maven2/com/github/hanlyjiang/ （需要在网站上发布，并且需要时间才能进入索引）
    const val Release_Upload_Url = "https://oss.sonatype.org/service/local/staging/deploy/maven2/"
    const val Release_Upload_Url_s01 = "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"

}