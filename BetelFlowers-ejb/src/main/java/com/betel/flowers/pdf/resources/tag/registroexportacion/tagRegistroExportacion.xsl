<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.1" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html"  encoding="UTF-8" indent="yes"  />   
    <xsl:param name="barcode"/> 
    <xsl:template match="/">
        <html xmlns="http://www.w3.org/1999/xhtml">
            <head>
                <title>report</title>		
                <link rel="stylesheet" href="styleRegExpo.css"></link>	
            </head>
            <body>
                <article>
                    <table class="metacontainer">
                        <th class="metaimage">
                            <img alt="">
                                <xsl:attribute name="src">
                                    <xsl:value-of select="concat(/etiquetaRegExpo/barcode,'.gif')"/>
                                </xsl:attribute>
                            </img>
                        </th>
                        <tr>
                            <td>
                                <xsl:value-of select="/etiquetaRegExpo/createDate"/>
                            </td>
                        </tr>
                    </table>
                    <table class="inventory">
                        <tr>
                            <th>VARIEDAD</th>
                            <th>FECHA VENCIMIENTO</th>
                        </tr>
                        <xsl:for-each select="/etiquetaRegExpo/detalle">
                            <tr>
                                <td>
                                    <xsl:value-of select="variedad" />
                                </td>
                                <td>
                                    <xsl:value-of select="fechaVencimiento" />
                                </td>
                            </tr>    
                        </xsl:for-each>
                    </table>             
                </article>
            </body>
        </html>

    </xsl:template>

</xsl:stylesheet>
