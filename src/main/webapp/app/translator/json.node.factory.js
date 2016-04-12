angular.module('formlyApp').factory(
    "JsonNode", function () {

        const NODE_TYPE_OBJECT = 'object';
        const NODE_TYPE_ARRAY  = 'array';
        const NODE_TYPE_LEAF   = 'leaf';

        function JsonNode(_key, _value, _currentPath, _parentNode) {
            this.key = _key;
            this.value = _value;
            this.currentPath = _currentPath;
            this.parentNode = _parentNode;

            this.currentPath.push(this.key);
            this.pathToNode = this.currentPath.slice();
            this.nodeType = JsonNode.getNodeType(this.value);
            this.children = [];

            if (!JsonNode.isLeafNode(this)) {
                angular.forEach(this.value, function (childVal, childKey) {
                    this.children.push(new JsonNode(childKey, childVal, this.currentPath, this));
                }, this);
            }
            this.currentPath = this.currentPath.splice(this.currentPath.length-1, 1);
        }

        JsonNode.isLeafNode = function (node) {
            return node.nodeType == NODE_TYPE_LEAF;
        };

        JsonNode.getNodeType = function(value) {
            if(angular.isObject(value)) {
                return NODE_TYPE_OBJECT
            } else if(angular.isArray(value)) {
                return NODE_TYPE_ARRAY
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


        // RecursiveIterator that will print out the structure of the JsonNode tree
        JsonNode.prototype = {
            toString: function (tabIndex, stringBuffer) {

                if (angular.isUndefined(tabIndex)) {
                    tabIndex = 0;
                } else {
                    tabIndex++;
                }

                if ( angular.isUndefined(stringBuffer) ) {
                    stringBuffer = '';
                }

                var padding = Array(tabIndex+1).join(" ");

                if (this.nodeType === NODE_TYPE_OBJECT) {
                    stringBuffer += padding + this.key + ':' + this.value;
                } else if (this.nodeType === NODE_TYPE_OBJECT) {

                    stringBuffer += padding + this.key + ": {";

                    angular.forEach(this.children, function(childNode) {
                        stringBuffer += childNode.toString(tabIndex, stringBuffer);
                    });

                    stringBuffer += padding + "}";
                }  else if (this.nodeType === NODE_TYPE_ARRAY) {
                    stringBuffer += padding + this.key + ": [";

                    angular.forEach(this.children, function(childNode) {
                        stringBuffer += childNode.toString(tabIndex, stringBuffer);
                    });

                    stringBuffer += padding + "]";
                }
            }
        };

        return (JsonNode);
    });