module AllFlightsReportCodec exposing (Investigation, decodeInvestigationList)


import Json.Encode
import Json.Decode
import Json.Decode.Pipeline
import Dict as Dict
import Date.Extra.Utils as DateUtils
import Date exposing (..)
import String

type alias Investigation =
    { flightNumber : Int
--    , when : Date
    , suite : String
    , test : String
    , success : Bool
    , grossDurationMillis : Maybe Int
    , netDurationMillis : Maybe Int
    , dataPointCount : Int

--    , command : Maybe Command
--    , context : Maybe Context
--    , log : Maybe (List String)
    }

--type alias Args =
--    { key : String
--    }

--type alias Command =
--    { name : String
--    , in_ : Maybe String
--    , bys : List (List (String, String))
----TODO: args are busted right now ...
--    , args : Dict.Dict String String
--    , expected : Maybe String
--    , expectedMany : Maybe (List String)
--    }

--type alias Context =
--    { failures : List String
--    , success : Maybe Bool
--    }

decodeInvestigationList : Json.Decode.Decoder (List Investigation)
decodeInvestigationList =
    Json.Decode.list <| decodeInvestigation

decodeInvestigation : Json.Decode.Decoder Investigation
decodeInvestigation =
    Json.Decode.Pipeline.decode Investigation
        |> Json.Decode.Pipeline.required "flightNumber" (Json.Decode.int)
--        |> Json.Decode.Pipeline.required "when" (Json.Decode.string |> Json.Decode.map DateUtils.unsafeFromString )
        |> Json.Decode.Pipeline.required "suite" (Json.Decode.string)
        |> Json.Decode.Pipeline.required "test" (Json.Decode.string)
        |> Json.Decode.Pipeline.required "success" (Json.Decode.string |> Json.Decode.map stringToBool)
--        |> Json.Decode.Pipeline.optional "command" (Json.Decode.maybe decodeDataPointCommand) Nothing
--        |> Json.Decode.Pipeline.optional "context" (Json.Decode.maybe decodeDataPointContext) Nothing
        |> Json.Decode.Pipeline.optional "grossDurationMillis" (Json.Decode.maybe (Json.Decode.int)) Nothing
        |> Json.Decode.Pipeline.optional "netDurationMillis" (Json.Decode.maybe (Json.Decode.int)) Nothing
        |> Json.Decode.Pipeline.required "dataPointCount" (Json.Decode.int)

stringToBool : String -> Bool
stringToBool value =
    let
        x = String.toLower value |> String.trim
    in
        if x == "true" then True else False

--decodeDataPointCommandArgs : Json.Decode.Decoder Args
--decodeDataPointCommandArgs =
--    Json.Decode.Pipeline.decode Args
--        |> Json.Decode.Pipeline.required "key" (Json.Decode.string)

--decodeDataPointCommand : Json.Decode.Decoder Command
--decodeDataPointCommand =
--    Json.Decode.Pipeline.decode Command
--        |> Json.Decode.Pipeline.required "name" (Json.Decode.string)
--        |> Json.Decode.Pipeline.optional "in" (Json.Decode.maybe Json.Decode.string) Nothing
--        |> Json.Decode.Pipeline.required "bys" (Json.Decode.list decodeBys)
----        |> Json.Decode.Pipeline.required "args" (decodeDataPointCommandArgs)
--        |> Json.Decode.Pipeline.required "args" (Json.Decode.dict Json.Decode.string)
--        |> Json.Decode.Pipeline.optional "expected" (Json.Decode.maybe Json.Decode.string) Nothing
--        |> Json.Decode.Pipeline.optional "expectedMany" (Json.Decode.maybe (Json.Decode.list Json.Decode.string)) Nothing
--
--
--decodeBys : Json.Decode.Decoder (List (String, String))
--decodeBys =
--    Json.Decode.keyValuePairs Json.Decode.string
--
--decodeDataPointContext : Json.Decode.Decoder Context
--decodeDataPointContext =
--    Json.Decode.Pipeline.decode Context
--        |> Json.Decode.Pipeline.required "failures" (Json.Decode.list Json.Decode.string)
--        |> Json.Decode.Pipeline.optional "success" (Json.Decode.maybe (Json.Decode.map (\v -> v) Json.Decode.bool)) Nothing
