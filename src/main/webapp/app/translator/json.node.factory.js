angular.module('translatrix').factory(
    "JsonNode", function () {

        const NODE_TYPE_OBJECT = 'object';
        const NODE_TYPE_ARRAY  = 'array';
        const NODE_TYPE_LEAF   = 'leaf';

        function JsonNode(_key, _value, _currentPath, _parentNode) {
            this.key = _key;
            this.value = _value;
            this.parentNode = _parentNode;

            _currentPath.push(this.key);
            this.nodeType = JsonNode.getNodeType(this.value);

            if (!JsonNode.isLeafNode(this)) {
                this.children = [];
                angular.forEach(this.value, function (childVal, childKey) {
                    this.children.push(new JsonNode(childKey, childVal, _currentPath, this));
                }, this);
            }
            _currentPath = _currentPath.splice(_currentPath.length - 1, 1);
        }

        JsonNode.isLeafNode = function (node) {
            return node.nodeType == NODE_TYPE_LEAF;
        };

        JsonNode.getNodeType = function(value) {
            if(angular.isArray(value)) {
                return NODE_TYPE_ARRAY
            } else if(angular.isObject(value)) {
                return NODE_TYPE_OBJECT
            } else if (angular.isString(value)) {
                return NODE_TYPE_LEAF;
            }
        };

        JsonNode.iterator = function (node, branchCallback, leafCallback) {
            angular.forEach(node.children, function (child) {
                if (JsonNode.isLeafNode(child.value)) {
                    leafCallback && leafCallback(child);
                } else {
                    branchCallback && branchCallback(child);
                }
            });
        };

        // Is this the last child node
        JsonNode.isLastChildNode = function (node) {
            var parent = node.parentNode;
            if (!angular.isUndefined(parent) ){
                return ( parent.children.indexOf(node) == parent.children.length -1 );
            }
            return false;
        };


        // RecursiveIterator that will print out the structure of the JsonNode tree
        JsonNode.prototype = {
            toString: function (tabIndex, stringBuffer, isArrayNode) {

                if (angular.isUndefined(tabIndex)) {
                    tabIndex = 0;
                } else {
                    tabIndex++;
                }

                if ( angular.isUndefined(stringBuffer) ) {
                    stringBuffer = '';
                }
                var isLastChildNode = JsonNode.isLastChildNode(this);

                var padding = Array(tabIndex+1).join('\t');

                if (this.nodeType === NODE_TYPE_LEAF) {
                    // Don't supply indexes as the keys to arrayNodes
                    if (!isArrayNode) {
                        stringBuffer += padding + '"' + this.key +'"' + ':' + '"'+ this.value + '"';
                    } else {
                        stringBuffer += padding + '"'+ this.value + '"';
                    }
                } else if (this.nodeType === NODE_TYPE_OBJECT) {
                    // Don't supply indexes as the keys to arrayNodes
                    if (!isArrayNode) {
                        stringBuffer += padding + (this.key? '"' + this.key + '":' : '' ) + '{\n';
                    } else {
                        stringBuffer += padding + '{\n';
                    }

                    angular.forEach(this.children, function(childNode) {
                        stringBuffer = childNode.toString(tabIndex, stringBuffer);
                    });

                    stringBuffer += padding + '}';


                }  else if (this.nodeType === NODE_TYPE_ARRAY) {

                    stringBuffer += padding + (this.key? '"' + this.key + '":' : '' ) + '[\n';


                    angular.forEach(this.children, function(childNode) {
                        stringBuffer = childNode.toString(tabIndex, stringBuffer, true);
                    });

                    stringBuffer += padding + ']';
                }

                stringBuffer += ( isLastChildNode || this.isRoot ? '':',') +'\n';

                return stringBuffer;
            }
        };

        return (JsonNode);
    });