base='resource-pack=http:\/\/0.0.0.0:10100\/resourcepack\/download\/'

ver=`curl 0.0.0.0:10100/resourcepack/version | awk '{ print substr ($0, 8 ) }' | awk '{ print substr( $0, 1, length($0)-1 ) }'`

regex='30s/.*/'$base$ver'/'
echo $regex

sed -i $regex server.properties
