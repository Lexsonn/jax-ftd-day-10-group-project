client initiated messages will be either *client* or *service* and either
  /createjson, /readjson, /updatejson, or /deletejson <objname>

  responses to *service* should be *plain*_foo_*, where the messagetype is one
  of:
    /createjson <objname>
      EENT - the object already exists and will not be created
      cjo_ready - ready to recieve file. The next *service* message from the
        client will be the json object. Response can either be:
          EMALFORMEDJSON - recieved obj not valid json
          EMISCERR - some other server error
          cjo_success - json object created under name

    /readjson <objname>
      ENOENT - object not available
      rjo_delivery - json object is the message

    /updatejson <objname>
      ENOENT - object not available
      ujo_ready - ready to recieve file. The next *service* message from the
        client will be the new json object. Response can either be:
          EMALFORMEDJSON - recieved obj not valid json
          EMISCERR - some other server error
          ujo_success - json object updated under name

    /deletejson <objname>
      ENOENT - object not available
      djo_success - object was deleted
