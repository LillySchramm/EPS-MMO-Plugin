base="resource-pack=http\:\/\/0.0.0.0\:10100\/resourcepack\/"

ver=`curl 0.0.0.0:10100/resourcepack/version | awk '{ print substr ($0, 8 ) }' | awk '{ print substr( $0, 1, length($0)-1 ) }'`

regex='30s/.*/'$base$ver'/'

sed -i $regex server.properties
