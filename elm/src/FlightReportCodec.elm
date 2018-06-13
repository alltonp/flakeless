module FlightReportCodec exposing (FlightDataRecord, DataPoint, Context, Command, decodeFlightDataRecord)


import Json.Encode
import Json.Decode
import Json.Decode.Pipeline
import Dict as Dict
import Date.Extra.Utils as DateUtils
import Date exposing (..)

----DataPoint is the thing ...
----need to make it a tree
--
--type Tree
--    = Tree Node
--
----TODO: so this becomes FlightDataRecord
----TODO: can still have a list of DataPoint
----TODO: copy level one should have suite
----TODO: is it a tree? or a list of trees? - in which maatbe DataPoint has the child list
--type alias Node =
--    { name : String
--    , children : List Tree
--    }
--
--
--
type alias FlightDataRecord =
   { suite: String
   , test: String
   , dataPoints: (List DataPoint)
   }

type alias DataPoint =
    { flightNumber : Int
    , when : Date
    , description : Maybe String
    , command : Maybe Command
    , context : Maybe Context
    , log : Maybe (List String)
    }

type alias Args =
    { key : String
    }

type alias Command =
    { name : String
    , in_ : Maybe String
    , bys : List (List (String, String))
--TODO: args are busted right now ...
    , args : Dict.Dict String String
    , expected : Maybe String
    , expectedMany : Maybe (List String)
    }

type alias Context =
    { failures : List String
    , success : Maybe Bool
    }


decodeFlightDataRecord : Json.Decode.Decoder FlightDataRecord
decodeFlightDataRecord =
    Json.Decode.Pipeline.decode FlightDataRecord
        |> Json.Decode.Pipeline.required "suite" Json.Decode.string
        |> Json.Decode.Pipeline.required "test" Json.Decode.string
        |> Json.Decode.Pipeline.required "dataPoints" decodeDataPointList

decodeDataPointList : Json.Decode.Decoder (List DataPoint)
decodeDataPointList =
    Json.Decode.list <| decodeDataPoint

decodeDataPoint : Json.Decode.Decoder DataPoint
decodeDataPoint =
    Json.Decode.Pipeline.decode DataPoint
        |> Json.Decode.Pipeline.required "flightNumber" (Json.Decode.int)
        |> Json.Decode.Pipeline.required "when" (Json.Decode.string |> Json.Decode.map DateUtils.unsafeFromString )
        |> Json.Decode.Pipeline.optional "description" (Json.Decode.maybe Json.Decode.string) Nothing
        |> Json.Decode.Pipeline.optional "command" (Json.Decode.maybe decodeDataPointCommand) Nothing
        |> Json.Decode.Pipeline.optional "context" (Json.Decode.maybe decodeDataPointContext) Nothing
        |> Json.Decode.Pipeline.optional "log" (Json.Decode.maybe (Json.Decode.list Json.Decode.string)) Nothing

--decodeDataPointCommandArgs : Json.Decode.Decoder Args
--decodeDataPointCommandArgs =
--    Json.Decode.Pipeline.decode Args
--        |> Json.Decode.Pipeline.required "key" (Json.Decode.string)

decodeDataPointCommand : Json.Decode.Decoder Command
decodeDataPointCommand =
    Json.Decode.Pipeline.decode Command
        |> Json.Decode.Pipeline.required "name" (Json.Decode.string)
        |> Json.Decode.Pipeline.optional "in" (Json.Decode.maybe Json.Decode.string) Nothing
        |> Json.Decode.Pipeline.required "bys" (Json.Decode.list decodeBys)
--        |> Json.Decode.Pipeline.required "args" (decodeDataPointCommandArgs)
        |> Json.Decode.Pipeline.required "args" (Json.Decode.dict Json.Decode.string)
        |> Json.Decode.Pipeline.optional "expected" (Json.Decode.maybe Json.Decode.string) Nothing
        |> Json.Decode.Pipeline.optional "expectedMany" (Json.Decode.maybe (Json.Decode.list Json.Decode.string)) Nothing


decodeBys : Json.Decode.Decoder (List (String, String))
decodeBys =
    Json.Decode.keyValuePairs Json.Decode.string

decodeDataPointContext : Json.Decode.Decoder Context
decodeDataPointContext =
    Json.Decode.Pipeline.decode Context
        |> Json.Decode.Pipeline.required "failures" (Json.Decode.list Json.Decode.string)
        |> Json.Decode.Pipeline.optional "success" (Json.Decode.maybe (Json.Decode.map (\v -> v) Json.Decode.bool)) Nothing
