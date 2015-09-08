package zhy2002.springexamples.algorithm;

import java.util.ArrayList;


public class Combinator<T> {

    private VisitListCallback<ArrayList<T>> _callback;
    private ArrayList<T> _source;
    private ArrayList<T> _result;
    private int _sourceLastIndex;

    public void combination(ArrayList<T> list, int selectCount, VisitListCallback<ArrayList<T>> callback)
    {
        if(selectCount > list.size()) throw new ArrayIndexOutOfBoundsException();

        _source = list;
        _sourceLastIndex = _source.size() - 1;
        _callback = callback;
        _result = new ArrayList<T>();
        combination(selectCount, 0);
    }

    private boolean combination(int selectCount, int startIndex)
    {
        boolean flagContinue = true;

        if(selectCount == 0)
        {
            flagContinue = _callback.Visit(_result);
        }
        else
        {
            int newResultLastIndex = _result.size();

            for(int i=startIndex; i<= _sourceLastIndex; i++)
            {
                _result.add(_source.get(i));
                flagContinue = combination(selectCount - 1, i);
                if(!flagContinue) break;
                _result.remove(newResultLastIndex);
            }
        }

        return  flagContinue;
    }


}
